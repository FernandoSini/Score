package com.flemis.score.features.app.data.datasource.remote

import com.flemis.score.features.app.data.models.ProductDetailsModel
import com.flemis.score.features.app.data.models.PurchaseModel
import com.flemis.score.features.app.data.models.PurchaseResultModel
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent

expect class InAppPurchase  {

    suspend fun initialize(onPurchasesUpdated: (PurchaseResultModel) -> Unit)
    suspend fun queryProducts(productsIds: List<String>): List<ProductDetailsModel>

    suspend fun makePurchase(product: ProductDetailsModel)

    suspend fun restorePurchase()

    val purchaseUpdates: SharedFlow<PurchaseResultModel>

    suspend fun getActivePurchases(): List<PurchaseModel>

    suspend fun dispose()
}