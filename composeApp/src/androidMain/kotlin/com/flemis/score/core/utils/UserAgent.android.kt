package com.flemis.score.core.utils

actual class UserAgent {
    actual fun getUserAgent(): String {
        System.getProperty("java.vm.name")
        System.getProperty("java.vm.arch")
        return System.getProperty("os.version")
    }
}