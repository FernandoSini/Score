package com.flemis.score.features.app.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flemis.score.features.app.data.models.UserModel

@Dao
interface UserDao {
    @Suppress("AndroidUnresolvedRoomSqlReference")
    @Query("SELECT * FROM user")
    suspend fun getData(): UserModel

}