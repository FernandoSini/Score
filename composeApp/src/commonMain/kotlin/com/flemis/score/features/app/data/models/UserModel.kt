package com.flemis.score.features.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flemis.score.features.app.domain.entities.UserEntity

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
fun UserModel.toEntity() = UserEntity(this.id)

