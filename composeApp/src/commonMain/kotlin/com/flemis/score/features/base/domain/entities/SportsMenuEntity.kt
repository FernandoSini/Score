package com.flemis.score.features.base.domain.entities

import com.flemis.score.features.base.data.models.SportsMenuModel

class SportsMenuEntity(
    val id: Long,
    val title: String,
    val icon: String,
    val label: String
)

fun SportsMenuEntity.toDomain(): SportsMenuModel = SportsMenuModel(this.id, this.title, this.icon, this.label)
