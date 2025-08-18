package com.flemis.score.features.app.data.datasource.remote

import org.koin.core.component.KoinComponent

class UserRemoteDataSourceImpl<E, T>(private val entityMapper: (E) -> T) : UserRemoteDataSource, KoinComponent {
}