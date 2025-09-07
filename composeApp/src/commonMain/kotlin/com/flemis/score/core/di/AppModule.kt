package com.flemis.score.core.di

import com.flemis.score.features.app.data.datasource.remote.BillingDataSource
import com.flemis.score.features.app.data.datasource.remote.BillingDataSourceImpl
import com.flemis.score.features.app.presentation.ui.viewmodel.AppViewModel
import com.flemis.score.features.home.data.datasource.local.SportsMenuLocalDataSource
import com.flemis.score.features.home.data.datasource.local.SportsMenuLocalDataSourceImpl
import com.flemis.score.features.home.data.repository.SportsMenuRepositoryImpl
import com.flemis.score.features.home.domain.repository.SportsMenuRepository
import com.flemis.score.features.home.domain.usecases.SportsMenuUseCase
import com.flemis.score.features.home.presentation.ui.viewmodel.SportsMenuViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val datasourceModule: Module = module {
    singleOf(::SportsMenuLocalDataSourceImpl).bind(SportsMenuLocalDataSource::class)
    singleOf(::BillingDataSourceImpl).bind(BillingDataSource::class)
}

val repositoryModule: Module = module {
    singleOf(::SportsMenuRepositoryImpl).bind(SportsMenuRepository::class)


}
val useCaseModule: Module = module {
    singleOf(::SportsMenuUseCase)
}

val viewModelModule: Module = module {
    viewModelOf(::SportsMenuViewModel)
    viewModelOf(::AppViewModel)
}


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(platformModule, viewModelModule, useCaseModule, repositoryModule, datasourceModule)
    }
}
