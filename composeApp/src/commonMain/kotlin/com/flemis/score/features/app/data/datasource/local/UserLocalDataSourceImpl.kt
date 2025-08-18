package com.flemis.score.features.app.data.datasource.local

import com.flemis.score.features.app.data.models.UserModel
import org.koin.core.component.KoinComponent

class UserLocalDataSourceImpl<E, T>(private val entityMapper: (E) -> T) : UserLocalDataSource, KoinComponent {
    override fun getUser(id: Long): UserModel? {
        TODO("Not yet implemented")
    }

}