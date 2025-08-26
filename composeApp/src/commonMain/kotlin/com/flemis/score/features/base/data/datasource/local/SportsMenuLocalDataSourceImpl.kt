package com.flemis.score.features.base.data.datasource.local

import com.flemis.score.core.utils.MultiPlatformPreferences
import com.flemis.score.features.app.data.datasource.local.BaseDataSource
import com.flemis.score.features.app.data.datasource.local.db.AppDatabaseBuilder
import com.flemis.score.features.base.data.models.SportsMenuModel
import com.flemis.score.features.base.data.models.toEntity
import com.flemis.score.features.base.domain.entities.SportsMenuEntity
import com.flemis.score.features.base.domain.entities.toDomain
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SportsMenuLocalDataSourceImpl(
    entityMapper: (SportsMenuModel) -> SportsMenuEntity,
    modelMapper: (SportsMenuEntity) -> SportsMenuModel
) :
    BaseDataSource<SportsMenuEntity, SportsMenuModel>(
        entityMapper = { it.toDomain() }, modelMapper = { it.toEntity() },
    ), SportsMenuLocalDataSource, KoinComponent {

    private val appDatabase: AppDatabaseBuilder by inject()
    private val _preferences: MultiPlatformPreferences by inject()
    override suspend fun getSportsMenuItems(): Result<List<SportsMenuEntity>> {
        val result = appDatabase.getRoomDatabase().getSportsMenuDao().getMenuItems()
        val sportsMenuItemsEntityResult = convertToEntity(result)
        return when (sportsMenuItemsEntityResult.isNotEmpty()) {
            true -> Result.success(sportsMenuItemsEntityResult)
            else -> Result.failure(Throwable("Menu should not be empty"))
        }
    }
}