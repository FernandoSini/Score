package com.flemis.score.features.home.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Query
import com.flemis.score.features.home.data.models.SportsMenuModel

@Dao
interface SportsMenuDao {
    @Suppress("AndroidUnresolvedRoomSqlReference")
    @Query("Select * from sports_menu")
    suspend fun getMenuItems(): List<SportsMenuModel>
}