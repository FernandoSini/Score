package com.flemis.score.features.app.data.datasource.local

import com.flemis.score.features.app.data.models.UserModel

interface UserLocalDataSource {
    fun getUser(id: Long): UserModel?

}