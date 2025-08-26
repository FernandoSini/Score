package com.flemis.score.features.base.data.datasource.local

import com.flemis.score.features.base.domain.entities.SportsMenuEntity

interface SportsMenuLocalDataSource {

   suspend fun getSportsMenuItems(): Result<List<SportsMenuEntity>>
}