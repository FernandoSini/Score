package com.flemis.score.features.app.data.models

data class PurchaseModel(
    val productId: String,
    val purchaseToken: String,
    val purchaseTime: Long,
    val orderId: String?,
    val quantity: Int = 1,
    val isAcknowledged: Boolean = false,
) {
}