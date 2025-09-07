package com.flemis.score.features.app.data.datasource.remote

import com.flemis.score.features.app.data.models.ProductDetailsModel
import com.flemis.score.features.app.data.models.PurchaseModel
import com.flemis.score.features.app.data.models.PurchaseResultModel
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BillingDataSourceImpl : BillingDataSource, KoinComponent {
    private val inAppPurchase: InAppPurchase by inject()
    override suspend fun initialize(onPurchasesUpdated: (PurchaseResultModel) -> Unit) =
        inAppPurchase.initialize(onPurchasesUpdated)

    override suspend fun queryProducts(productsIds: List<String>): List<ProductDetailsModel> =
        inAppPurchase.queryProducts(productsIds)

    override suspend fun makePurchase(productId: String) {
        val product = inAppPurchase.queryProducts(listOf(productId)).firstOrNull()
        if (product != null) {
            inAppPurchase.makePurchase(product)
        }
    }

    override suspend fun restorePurchase() = inAppPurchase.restorePurchase()

    override val purchaseUpdates: SharedFlow<PurchaseResultModel> = inAppPurchase.purchaseUpdates

    override suspend fun getActivePurchases(): List<PurchaseModel> = inAppPurchase.getActivePurchases()

    override suspend fun dispose() = inAppPurchase.dispose()

}