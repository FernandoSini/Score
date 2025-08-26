package com.flemis.score.core.utils

expect class MultiPlatformPreferences {
    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String?): String?
    fun putInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int): Int
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun putFloat(key: String, value: Float)
    fun getFloat(key: String, defaultValue: Float): Float
    fun putLong(key: String, value: Long)
    fun getLong(key: String, defaultValue: Long): Long
    fun putStringSet(key: String, value: Set<String>)
    fun getStringSet(key: String, defaultValue: Set<String>): Set<String?>?
    fun putMap(key: String, value: Map<String, String>)
    fun getMap(key: String): Map<String, String>
    fun clearPreferences(key: String?)
}