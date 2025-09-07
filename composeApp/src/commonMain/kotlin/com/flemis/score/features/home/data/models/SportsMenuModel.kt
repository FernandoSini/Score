package com.flemis.score.features.home.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flemis.score.features.app.data.models.UserModel
import com.flemis.score.features.home.domain.entities.SportsMenuEntity

@Entity("sports_menu")
data class SportsMenuModel(@PrimaryKey val id: Long, val title: String, val icon: String, val label: String) {
}

fun SportsMenuModel.toEntity(): SportsMenuEntity = SportsMenuEntity(this.id, this.title, this.icon, this.label)