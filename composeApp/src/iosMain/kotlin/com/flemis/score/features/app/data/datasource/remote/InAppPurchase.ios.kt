package com.flemis.score.features.app.data.datasource.remote

import com.flemis.score.features.app.data.models.ProductDetailsModel
import com.flemis.score.features.app.data.models.PurchaseModel
import com.flemis.score.features.app.data.models.PurchaseResultModel
import kotlinx.coroutines.flow.SharedFlow

actual class InAppPurchase {
    private val iosIap = InAppPurchaseHelper()
    actual suspend fun initialize(onPurchasesUpdated: (PurchaseResultModel) -> Unit) =
        iosIap.initialize(onPurchasesUpdated)

    actual suspend fun queryProducts(productsIds: List<String>): List<ProductDetailsModel> =
        iosIap.queryProducts(productsIds)

    actual suspend fun makePurchase(product: ProductDetailsModel) = iosIap.makePurchase(product)

    actual suspend fun restorePurchase() = iosIap.restorePurchase()

    actual val purchaseUpdates: SharedFlow<PurchaseResultModel> = iosIap.purchaseUpdates
    actual suspend fun getActivePurchases(): List<PurchaseModel> = iosIap.getActivePurchases()

    actual suspend fun dispose() = iosIap.dispose()
}