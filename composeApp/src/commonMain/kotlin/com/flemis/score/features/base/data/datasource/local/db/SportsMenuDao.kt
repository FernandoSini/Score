package com.flemis.score.features.base.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Query
import com.flemis.score.features.base.data.models.SportsMenuModel

@Dao
interface SportsMenuDao {
    @Suppress("AndroidUnresolvedRoomSqlReference")
    @Query("Select * from sports_menu")
    suspend fun getMenuItems(): List<SportsMenuModel>
}