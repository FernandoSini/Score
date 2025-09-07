package com.flemis.score.core.utils

actual class UserAgent {
    actual fun getUserAgent(): String {
        System.getProperty("java.vm.name")
        System.getProperty("java.vm.arch")
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        return System.getProperty("os.version").toString()
    }
}