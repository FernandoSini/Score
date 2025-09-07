package com.flemis.score.features.app.data.datasource.remote

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.flemis.score.features.app.data.models.ProductDetailsModel
import com.flemis.score.features.app.data.models.PurchaseModel
import com.flemis.score.features.app.data.models.PurchaseResultModel
import com.flemis.score.features.app.data.models.enums.ProductType
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.collections.emptyList
import kotlin.coroutines.resume

actual class InAppPurchase(private val context: Context) :
    PurchasesUpdatedListener, BillingClientStateListener {
    private lateinit var billingClient: BillingClient;
    private var onPurchaseCallback: ((PurchaseResultModel) -> Unit)? = null
    private val _purchaseUpdates = MutableSharedFlow<PurchaseResultModel>(extraBufferCapacity = 1)
    actual val purchaseUpdates: SharedFlow<PurchaseResultModel> = _purchaseUpdates
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    actual suspend fun initialize(onPurchasesUpdated: (PurchaseResultModel) -> Unit) {
        this.onPurchaseCallback = onPurchasesUpdated
        billingClient = BillingClient.newBuilder(this.context).setListener(this).enableUserChoiceBilling {
            it.products.forEach { product -> Napier.d { product.id } }
        }.build()
        billingClient.startConnection(this)
    }

    actual suspend fun queryProducts(productsIds: List<String>): List<ProductDetailsModel> =
        suspendCancellableCoroutine { continuation ->
            val products = productsIds.map { productId ->
                QueryProductDetailsParams.Product.newBuilder().setProductId(productId).build()
            }

            val params = QueryProductDetailsParams.newBuilder().setProductList(products).build()
            billingClient.queryProductDetailsAsync(params) { billingResult, productDetails ->
                productDetails.productDetailsList.forEach { details -> }
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    val mappedProducts = productDetails.productDetailsList.map { product ->
                        product.subscriptionOfferDetails
                        ProductDetailsModel(
                            productId = product.productId,
                            name = product.name,
                            description = product.description,
                            price = product.oneTimePurchaseOfferDetails?.formattedPrice ?: "N/A",
                            currencyCode = product.oneTimePurchaseOfferDetails?.priceCurrencyCode ?: "N/A",
                            priceAmountMicros = product.oneTimePurchaseOfferDetails?.priceAmountMicros ?: 0,
                            type = when (product.productType) {
                                BillingClient.ProductType.INAPP -> ProductType.NON_CONSUMABLE
                                BillingClient.ProductType.SUBS -> ProductType.SUBSCRIPTION
                                else -> ProductType.NON_CONSUMABLE
                            },
                        )
                    }
                    continuation.resume(mappedProducts)
                    saveAndroidProducts(mappedProducts)

                } else {
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Houve um problema:  ${billingResult.debugMessage}", billingResult.responseCode
                        )
                    )
                    continuation.resume(emptyList())
                }

            }
        }


    actual suspend fun makePurchase(product: ProductDetailsModel) {
        if (context !is Activity) {
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Context should not be activity", null))
            return
        }

        val productsToPurchase = skProductsAndroid[product.productId]

        if (productsToPurchase == null) {
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Product not found", null))
        }
        val offerToken = productsToPurchase?.subscriptionOfferDetails?.firstOrNull()?.offerToken
        if (offerToken == null) {
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Offer token not found", null))
        }

        val detailsParams = BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productsToPurchase!!)
            .setOfferToken(offerToken.toString()).build()
        val billingFlowParams =
            BillingFlowParams.newBuilder().setProductDetailsParamsList(listOf(detailsParams)).build()

        billingClient.launchBillingFlow(context, billingFlowParams)

    }

    actual suspend fun restorePurchase() {
        coroutineScope.launch {
            queryActivePurchasesInternal()
        }
    }


    actual suspend fun getActivePurchases(): List<PurchaseModel> {
        return queryActivePurchasesInternal()
    }

    actual suspend fun dispose() {
        if (billingClient.isReady) {
            billingClient.endConnection()
            Napier.w { "Connection has been closed" }
        }
        coroutineScope.cancel()
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase?>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            onPurchaseCallback?.invoke(PurchaseResultModel.UserCancelled)
            _purchaseUpdates.tryEmit(PurchaseResultModel.UserCancelled)
        } else {
            onPurchaseCallback?.invoke(
                PurchaseResultModel.Error(
                    "Callback: houve um erro ao atualizar a compras:${billingResult.debugMessage}",
                    billingResult.responseCode
                )
            )
            _purchaseUpdates.tryEmit(
                PurchaseResultModel.Error(
                    "Houve um erro ao atualizar compras: ${billingResult.debugMessage}", billingResult.responseCode
                )
            )
        }

    }

    private fun handlePurchase(purchase: Purchase?) {
        if (purchase?.purchaseState == Purchase.PurchaseState.PURCHASED) {

            var result = Result.failure<String>(Exception("Purchase não validada"))
            coroutineScope.launch {
                result = sendReceiptToBackEnd(purchase)
            }

            if (result.isSuccess) {
                val commonModel: PurchaseModel = PurchaseModel(
                    purchase.products.first(),
                    purchaseToken = purchase.purchaseToken,
                    orderId = purchase.orderId,
                    quantity = purchase.quantity,
                    purchaseTime = purchase.purchaseTime,
                    isAcknowledged = purchase.isAcknowledged,
                )
                onPurchaseCallback?.invoke(PurchaseResultModel.Success(commonModel))
                _purchaseUpdates.tryEmit(PurchaseResultModel.Success(commonModel))

                if (!purchase.isAcknowledged) {
                    acknowledgePurchase(purchase)
                } else {
                    if (purchase.products.firstOrNull()?.contains("consumable") == true) {
                        consumePurchase(purchase)
                    }
                }
            } else {
                onPurchaseCallback?.invoke(
                    PurchaseResultModel.Error(
                        "Receipt callback error: ${result.exceptionOrNull()?.message}",
                        null
                    )
                )
                _purchaseUpdates.tryEmit(
                    PurchaseResultModel.Error(
                        "Receipt purchase error: ${result.exceptionOrNull()?.message}",
                        null
                    )
                )
            }
        } else if (purchase?.purchaseState == Purchase.PurchaseState.PENDING) {
            onPurchaseCallback?.invoke(PurchaseResultModel.Pending)
            _purchaseUpdates.tryEmit(PurchaseResultModel.Pending)
        } else {
            onPurchaseCallback?.invoke(PurchaseResultModel.Error("Houve um erro ao processar compra", null))
            _purchaseUpdates.tryEmit(PurchaseResultModel.Error("Houve um erro ao processar compra", null))
        }
    }

    override fun onBillingServiceDisconnected() {
        _purchaseUpdates.tryEmit(PurchaseResultModel.Error("IAP disconnected", null))
        billingClient.startConnection(this)
    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            coroutineScope.launch {
                queryActivePurchasesInternal()
            }
        } else {
            _purchaseUpdates.tryEmit(
                PurchaseResultModel.Error(
                    "Error while querying active purchases from server ${billingResult.debugMessage}",
                    errorCode = billingResult.responseCode
                )
            )
        }
    }

    private var skProductsAndroid: Map<String, ProductDetails> = emptyMap()

    @Suppress("UNCHECKED_CAST")
    private fun saveAndroidProducts(productDetailsList: List<ProductDetailsModel>) {
        skProductsAndroid = (productDetailsList as List<ProductDetails>).associateBy { it.productId }
    }

    private suspend fun queryActivePurchasesInternal(): List<PurchaseModel> =
        suspendCancellableCoroutine { continuation ->
            if (!billingClient.isReady) {
                continuation.resume(emptyList())
                return@suspendCancellableCoroutine
            }
            val inAppParams = QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()
            val subsParam = QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()

            billingClient.queryPurchasesAsync(inAppParams) { inAppResult, inAppResponseList ->
                if (inAppResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    billingClient.queryPurchasesAsync(subsParam) { subsResult, subsResponseList ->
                        if (subsResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            val allPurchases = mutableListOf<PurchaseModel>()
                            (inAppResponseList + subsResponseList).forEach { purchase ->
                                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                                    allPurchases.add(
                                        PurchaseModel(
                                            productId = purchase.products.first(),
                                            purchaseToken = purchase.purchaseToken,
                                            orderId = purchase.orderId,
                                            quantity = purchase.quantity,
                                            purchaseTime = purchase.purchaseTime,
                                            isAcknowledged = purchase.isAcknowledged,
                                        )
                                    )
                                    if (!purchase.isAcknowledged) {
                                        acknowledgePurchase(purchase)
                                    }
                                }
                            }
                            continuation.resume(allPurchases)
                        } else {
                            _purchaseUpdates.tryEmit(
                                PurchaseResultModel.Error(
                                    "Erro ao consultar assinaturas ${subsResult.debugMessage}",
                                    subsResult.responseCode
                                )
                            )
                            Napier.e { "Subscriptions error: ${subsResult.debugMessage}" }
                            continuation.resume(emptyList())
                        }
                    }
                } else {
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Error ao consutar compras InApp ${inAppResult.debugMessage}",
                            inAppResult.responseCode
                        )
                    )
                    Napier.e { "InAppError error: ${inAppResult.debugMessage}" }
                    continuation.resume(emptyList())

                }
            }

        }


    private fun acknowledgePurchase(purchase: Purchase) {

        val acknowledgePurchaseParams =
            AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()

        billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    consumePurchase(purchase)
                }

                else -> {
                    //ou fazer uma request para solicitar o refound por enquanto nao tenho o endpoint entao vou deixar aqui
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Para solicitar estorno, acesse o Google Play ou entre em contato com o suporte.",
                            null
                        )
                    )
                }

            }


        }
    }

    private fun sendReceiptToBackEnd(purchase: Purchase): Result<String> {

        //metodo para enviar o token pro backend
        val body = mapOf(
            "purchaseToken" to purchase.purchaseToken,
            "productId" to purchase.products.first(),
            "orderId" to purchase.orderId,
            "signature" to purchase.signature,
        )

        return Result.failure(Exception(""))
    }

    private fun consumePurchase(purchase: Purchase) {
        val consumeParam = ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        billingClient.consumeAsync(consumeParam) { billingResult, purchaseToken ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Success(
                            PurchaseModel(
                                productId = purchase.products.first() ?: "uknown",
                                purchaseToken = consumeParam.purchaseToken,
                                purchaseTime = purchase.purchaseTime,
                                isAcknowledged = purchase.isAcknowledged,
                                quantity = purchase.quantity,
                                orderId = purchase.orderId
                            )
                        )
                    )
                }

                else -> {
                    _purchaseUpdates.tryEmit(
                        PurchaseResultModel.Error(
                            "Error ao consumir compra",
                            billingResult.responseCode
                        )
                    )
                }
            }
        }

    }

}