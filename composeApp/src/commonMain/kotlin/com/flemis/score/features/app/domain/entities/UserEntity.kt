package com.flemis.score.features.app.domain.entities

import com.flemis.score.features.app.data.models.UserModel

class UserEntity(
    val id: Long = 0L
)

fun UserEntity.toDomain() = UserModel(this.id)