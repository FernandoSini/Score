package com.flemis.score.features.app.data.repository

import com.flemis.score.features.app.data.datasource.local.UserLocalDataSource
import com.flemis.score.features.app.data.datasource.remote.UserRemoteDataSource
import com.flemis.score.features.app.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepositoryImpl : UserRepository, KoinComponent {
    private val userLocalDataSource: UserLocalDataSource by inject();
    private val userRemoteDataSource: UserRemoteDataSource by inject();
}