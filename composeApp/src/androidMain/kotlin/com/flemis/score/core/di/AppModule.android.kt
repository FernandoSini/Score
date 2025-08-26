package com.flemis.score.core.di

import android.content.Context
import com.flemis.score.core.utils.MultiPlatformPreferences
import com.flemis.score.core.utils.UserAgent
import com.flemis.score.features.app.data.datasource.local.db.AppDatabaseBuilder
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


actual val platformModule: Module = module {
    single<AppDatabaseBuilder> { AppDatabaseBuilder(get()) }
    single<UserAgent>{UserAgent()}
    single<MultiPlatformPreferences>{MultiPlatformPreferences(get())}

}

fun androidModule(context: Context): Module = module {

}



