package com.flemis.score.features.base.data.repository

import com.flemis.score.features.base.data.datasource.local.SportsMenuLocalDataSource
import com.flemis.score.features.base.domain.entities.SportsMenuEntity
import com.flemis.score.features.base.domain.repository.SportsMenuRepository
import io.github.aakira.napier.Napier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SportsMenuRepositoryImpl : SportsMenuRepository, KoinComponent {
    private val sportsMenuLocalDataSource: SportsMenuLocalDataSource by inject()
    override suspend fun getSportsMenu(): List<SportsMenuEntity> {
        val sportsMenuList = sportsMenuLocalDataSource.getSportsMenuItems().fold(
            onSuccess = { entity -> entity },
            onFailure = { error ->
                Napier.e { error.message.toString() }
                mutableListOf()
            }
        )
        return sportsMenuList
    }


}