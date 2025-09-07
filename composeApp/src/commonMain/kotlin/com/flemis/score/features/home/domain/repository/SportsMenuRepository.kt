package com.flemis.score.features.home.domain.repository

import com.flemis.score.features.home.domain.entities.SportsMenuEntity

interface SportsMenuRepository {
    suspend fun getSportsMenu(): List<SportsMenuEntity>
}