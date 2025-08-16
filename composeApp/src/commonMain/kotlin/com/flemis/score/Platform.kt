package com.flemis.score

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform