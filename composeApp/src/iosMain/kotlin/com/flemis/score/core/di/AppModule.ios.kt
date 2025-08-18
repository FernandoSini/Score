package com.flemis.score.core.di

import com.flemis.score.core.utils.UserAgent
import com.flemis.score.features.app.data.datasource.local.db.AppDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDatabaseBuilder> { AppDatabaseBuilder() }
    single<UserAgent>{ UserAgent() }
}
