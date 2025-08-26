package com.flemis.score.features.base.domain.repository

import com.flemis.score.features.base.domain.entities.SportsMenuEntity

interface SportsMenuRepository {
    suspend fun getSportsMenu(): List<SportsMenuEntity>
}