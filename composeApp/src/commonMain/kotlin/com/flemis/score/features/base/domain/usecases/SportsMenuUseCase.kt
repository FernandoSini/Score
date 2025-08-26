package com.flemis.score.features.base.domain.usecases

import com.flemis.score.features.base.domain.entities.SportType
import com.flemis.score.features.base.domain.entities.SportsMenuEntity
import com.flemis.score.features.base.domain.repository.SportsMenuRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SportsMenuUseCase : KoinComponent {
    private val sportsMenuRepository: SportsMenuRepository by inject()

    suspend fun getMenuItems(): List<SportsMenuEntity> {

        return sportsMenuRepository.getSportsMenu()

    }


}