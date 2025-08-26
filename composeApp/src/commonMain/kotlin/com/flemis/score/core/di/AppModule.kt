package com.flemis.score.core.di

import com.flemis.score.features.base.data.datasource.local.SportsMenuLocalDataSource
import com.flemis.score.features.base.data.datasource.local.SportsMenuLocalDataSourceImpl
import com.flemis.score.features.base.data.repository.SportsMenuRepositoryImpl
import com.flemis.score.features.base.domain.repository.SportsMenuRepository
import com.flemis.score.features.base.domain.usecases.SportsMenuUseCase
import com.flemis.score.features.base.presentation.ui.viewmodel.SportsMenuViewModel
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
}

val repositoryModule: Module = module {
    singleOf(::SportsMenuRepositoryImpl).bind(SportsMenuRepository::class)


}
val useCaseModule: Module = module {
    singleOf(::SportsMenuUseCase)
}

val viewModelModule: Module = module {
    viewModelOf(::SportsMenuViewModel)
}


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(platformModule, viewModelModule, useCaseModule, repositoryModule, datasourceModule)
    }
}
