package com.flemis.score.features.app.data.datasource.remote

import com.flemis.score.features.app.data.models.ProductDetailsModel
import com.flemis.score.features.app.data.models.PurchaseModel
import com.flemis.score.features.app.data.models.PurchaseResultModel
import com.flemis.score.features.app.data.models.enums.ProductType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.io.IOException
import platform.Foundation.NSError
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSURL
import platform.Foundation.currencyCode
import platform.Foundation.timeIntervalSince1970
import platform.StoreKit.SKErrorCode
import platform.StoreKit.SKPayment
import platform.StoreKit.SKPaymentQueue
import platform.StoreKit.SKPaymentTransaction
import platform.StoreKit.SKPaymentTransactionObserverProtocol
import platform.StoreKit.SKPaymentTransactionState
import platform.StoreKit.SKProduct
import platform.StoreKit.SKProductsRequest
import platform.StoreKit.SKProductsRequestDelegateProtocol
import platform.StoreKit.SKProductsResponse
import platform.StoreKit.SKRequest
import platform.UIKit.UIApplication
import platform.darwin.NSObject
import platform.Foundation.*


class InAppPurchaseHelper : NSObject(), SKProductsRequestDelegateProtocol, SKPaymentTransactionObserverProtocol {

    private var onPurchasesUpdatedCallback: ((PurchaseResultModel) -> Unit)? = null
    private val _purchasesUpdate = MutableSharedFlow<PurchaseResultModel>(extraBufferCapacity = 1)
    val purchaseUpdates: SharedFlow<PurchaseResultModel> = _purchasesUpdate

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var skProducts: Map<String, SKProduct> = emptyMap()
    private var mappedProducts: List<ProductDetailsModel>? = null

    suspend fun initialize(onPurchasesUpdated: (PurchaseResultModel) -> Unit) {
        this.onPurchasesUpdatedCallback = onPurchasesUpdated
        SKPaymentQueue.defaultQueue().addTransactionObserver(this)
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun queryProducts(productsIds: List<String>): List<ProductDetailsModel> =
        suspendCancellableCoroutine { continuation ->
            val productRequest = SKProductsRequest(productIdentifiers = productsIds.toSet())
            val response = SKProductsResponse()
            productsRequest(productRequest, response)
            if (!mappedProducts.isNullOrEmpty()) {
                continuation.resumeWith(Result.success(mappedProducts!!))
            }
            productRequest.start()
        }


    suspend fun makePurchase(product: ProductDetailsModel) {
        val selectedProduct = skProducts[product.productId]
        if (selectedProduct != null) {
            val payment = SKPayment.paymentWithProduct(selectedProduct)
            SKPaymentQueue.defaultQueue().addPayment(payment)

        } else {
            _purchasesUpdate.tryEmit(
                PurchaseResultModel.Error(
                    "Product not found",
                    SKErrorCode.SKErrorStoreProductNotAvailable.value.toInt()
                )
            )
        }
    }

    suspend fun restorePurchase() {
        SKPaymentQueue.defaultQueue().restoreCompletedTransactions()
    }


    suspend fun getActivePurchases(): List<PurchaseModel> {
        val activeTransactions = SKPaymentQueue.defaultQueue().transactions.filterIsInstance<SKPaymentTransaction>()
            .filter { transaction ->
                transaction.transactionState == SKPaymentTransactionState.SKPaymentTransactionStatePurchased
                        || transaction.transactionState == SKPaymentTransactionState.SKPaymentTransactionStateRestored
            }
        return activeTransactions.mapNotNull { it.toCommonPurchase() }
    }

    suspend fun dispose() {
        SKPaymentQueue.Companion.defaultQueue().removeTransactionObserver(this)
        coroutineScope.cancel()
        SKPaymentQueue.defaultQueue().finalize()
        SKPaymentQueue.defaultQueue().transactions.filterIsInstance<SKPaymentTransaction>().forEach {
            SKPaymentQueue.Companion.defaultQueue().finishTransaction(it)
        }

    }

    @Throws(Exception::class, IOException::class)
    override fun request(request: SKRequest, didFailWithError: NSError) {
        _purchasesUpdate.tryEmit(
            PurchaseResultModel.Error(
                "Unable to request product: ${didFailWithError.localizedDescription}",
                didFailWithError.code.toInt(),
            )
        )
    }

    @Suppress("UNCHECKED_CAST")

    override fun productsRequest(
        request: SKProductsRequest,
        didReceiveResponse: SKProductsResponse
    ) {
        request.delegate = this
        val foundProducts = didReceiveResponse.products as List<SKProduct>
        skProducts = foundProducts.associateBy { it.productIdentifier }
        mappedProducts = foundProducts.map { item ->

            val formatter = NSNumberFormatter()
            formatter.numberStyle = NSNumberFormatterDecimalStyle
            formatter.locale = item.priceLocale
            val priceFormatted = formatter.stringFromNumber(item.price) ?: ""


            val priceAmountMicros = (item.price.doubleValue * 1_000_000.0).toLong()

            ProductDetailsModel(
                item.productIdentifier,
                name = item.localizedTitle,
                description = item.localizedDescription,
                price = priceFormatted,
                currencyCode = item.priceLocale.currencyCode ?: "N/A",
                priceAmountMicros = priceAmountMicros,
                type = ProductType.NON_CONSUMABLE
            )

        }

    }

    @Suppress("UNCHECKED_CAST")
    override fun paymentQueue(
        queue: SKPaymentQueue,
        updatedTransactions: List<*>
    ) {
        (updatedTransactions as List<SKPaymentTransaction>).forEach { transaction ->

            when (transaction.transactionState) {
                SKPaymentTransactionState.SKPaymentTransactionStateDeferred -> _purchasesUpdate.tryEmit(
                    PurchaseResultModel.Pending
                )

                SKPaymentTransactionState.SKPaymentTransactionStateFailed -> handleTransactionFailed(transaction)
                SKPaymentTransactionState.SKPaymentTransactionStatePurchasing -> _purchasesUpdate.tryEmit(
                    PurchaseResultModel.Pending
                )

                SKPaymentTransactionState.SKPaymentTransactionStateRestored -> handleTransactionRestored(transaction)
                SKPaymentTransactionState.SKPaymentTransactionStatePurchased -> handleTransactionPurchased(transaction)

                else -> {
                    _purchasesUpdate.tryEmit(
                        PurchaseResultModel.Error(
                            "Unspecified State",
                            SKErrorCode.SKErrorUnknown.value.toInt()
                        )
                    )
                }

            }
        }

    }

    private fun handleTransactionPurchased(transaction: SKPaymentTransaction) {
        val commonPurchase = transaction.toCommonPurchase()


        if (commonPurchase != null) {
            onPurchasesUpdatedCallback?.invoke(PurchaseResultModel.Success(commonPurchase))
            _purchasesUpdate.tryEmit(PurchaseResultModel.Success(commonPurchase))


            val receiptUrl = NSBundle.mainBundle.appStoreReceiptURL
            if (receiptUrl != null) {
                val receiptData = NSData.dataWithContentsOfURL(receiptUrl)
                receiptData?.base64EncodedDataWithOptions(0u)?.let { base64Receipt ->

                    val response = sendReceiptToBackend(base64Receipt.base64Encoding())
                } ?: run {
                    _purchasesUpdate.tryEmit(PurchaseResultModel.Error("", SKErrorCode.SKErrorUnknown.value.toInt()))
                }
            } else {
                onPurchasesUpdatedCallback?.invoke(
                    PurchaseResultModel.Error(
                        "Receipt was null",
                        SKErrorCode.SKErrorClientInvalid.value.toInt()
                    )
                )
                _purchasesUpdate.tryEmit(
                    PurchaseResultModel.Error(
                        "Receipt was null",
                        SKErrorCode.SKErrorClientInvalid.value.toInt()
                    )
                )
                SKPaymentQueue.defaultQueue().finishTransaction(transaction)
            }

        } else {
            _purchasesUpdate.tryEmit(
                PurchaseResultModel.Error(
                    "Error while trying to purchase product",
                    SKErrorCode.SKErrorPaymentInvalid.value.toInt()
                )
            )
            SKPaymentQueue.defaultQueue().finishTransaction(transaction)
        }
    }


    private fun handleTransactionRestored(transaction: SKPaymentTransaction) {
        val commonPurchase = transaction.toCommonPurchase()
        if (commonPurchase != null) {
            onPurchasesUpdatedCallback?.invoke(PurchaseResultModel.Success(commonPurchase))
            _purchasesUpdate.tryEmit(PurchaseResultModel.Success(commonPurchase))
        }
        SKPaymentQueue.defaultQueue().finishTransaction(transaction)
    }


    private fun handleTransactionFailed(transaction: SKPaymentTransaction) {
        val error = transaction.error
        val errorMessage = error?.localizedDescription ?: "Não"
        val errorCode = (error?.code ?: -1).toInt()

        if (errorCode == SKErrorCode.SKErrorPaymentCancelled.value.toInt()) {
            onPurchasesUpdatedCallback?.invoke(PurchaseResultModel.UserCancelled)
            _purchasesUpdate.tryEmit(PurchaseResultModel.UserCancelled)
        } else {
            onPurchasesUpdatedCallback?.invoke(PurchaseResultModel.Error(errorMessage, errorCode))
        }
        SKPaymentQueue.defaultQueue().finishTransaction(transaction)
    }


    private fun SKPaymentTransaction.toCommonPurchase(): PurchaseModel? {
        val productId = payment.productIdentifier
        val purchaseToken = transactionIdentifier ?: return null
        val purchaseTime = transactionDate?.timeIntervalSince1970?.times(1000)?.toLong() ?: 0L

        return PurchaseModel(
            productId = productId,
            purchaseToken = purchaseToken,
            purchaseTime = purchaseTime,
            quantity = payment.quantity.toInt(),
            orderId = originalTransaction?.transactionIdentifier,
            isAcknowledged = true
        )
    }

    fun openAppleRefundSupport() {
        val url = "https://support.apple.com/pt-br/HT204084"
        UIApplication.sharedApplication.openURL(
            url = NSURL(string = url),
            options = emptyMap<Any?, Any>(),
            completionHandler = null
        )

    }

    fun openAppleSubscriptionManagement() {
        val url = "https://apps.apple.com/account/subscriptions"
        UIApplication.sharedApplication.openURL(
            NSURL(string = url),
            options = emptyMap<Any?, Any>(),
            completionHandler = null
        )
    }

    private fun sendReceiptToBackend(base64Receipt: String) {}


}