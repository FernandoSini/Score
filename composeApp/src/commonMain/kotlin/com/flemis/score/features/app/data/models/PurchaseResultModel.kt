package com.flemis.score.features.app.data.models

sealed class PurchaseResultModel {
    data class Success(val result: PurchaseModel) : PurchaseResultModel()
    data class Error(val message: String, val errorCode: Int?) : PurchaseResultModel()
    object UserCancelled : PurchaseResultModel()
    object Pending : PurchaseResultModel()
}