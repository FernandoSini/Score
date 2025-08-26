package com.flemis.score.core.utils

import kotlinx.datetime.LocalDateTime


fun LocalDateTime.toNormalDate(): String {
    return this.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }.substring(0, 3).plus(", ") +
            this.day.toString()
                .plus(" ") + this.month.name.lowercase().replaceFirstChar { it.uppercase() }
        .plus(" ") + this.year.toString().plus("-") + this.time.toString()
        .substring(0, 5).plus(" ") + if (this.time.hour <= 12) "AM" else "PM"
}