package com.flemis.score.features.app.data.models

import com.flemis.score.features.app.data.models.enums.ProductType

data class ProductDetailsModel(
    val productId: String,
    val name: String,
    val description: String,
    val price: String,
    val currencyCode: String,
    val priceAmountMicros: Long,
    val type: ProductType
)