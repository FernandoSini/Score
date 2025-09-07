package com.flemis.score.features.home.data.datasource.local

import com.flemis.score.features.home.domain.entities.SportsMenuEntity

interface SportsMenuLocalDataSource {

   suspend fun getSportsMenuItems(): Result<List<SportsMenuEntity>>
}