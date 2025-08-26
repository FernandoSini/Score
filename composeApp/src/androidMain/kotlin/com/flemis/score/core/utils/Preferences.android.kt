package com.flemis.score.core.utils

import android.content.Context
import android.content.SharedPreferences
import io.github.aakira.napier.Napier


actual class MultiPlatformPreferences(private val context: Context) {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    actual fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        if (defaultValue != null) {
            return preferences.getString(key, defaultValue)
        }else{
            return preferences.getString(key, "")
        }
    }

    actual fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    actual fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    actual fun putFloat(key: String, value: Float) {
        preferences.edit().putFloat(key, value).apply()
    }

    actual fun getFloat(key: String, defaultValue: Float): Float {
        return preferences.getFloat(key, defaultValue)
    }

    actual fun putLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    actual fun putStringSet(key: String, value: Set<String>) {
        preferences.edit().putStringSet(key, value).apply()
    }

    actual fun getStringSet(key: String, defaultValue: Set<String>): Set<String?>? {
        return preferences.getStringSet(key, defaultValue)
    }

    actual fun putMap(key: String, value: Map<String, String>) {
        val editor = preferences.edit()
        for ((k, v) in value) {
            editor.putString("$key.$k", v)
        }
        editor.apply()
        Napier.d { "Android saved $key = $value" }
    }

    actual fun getMap(key: String): Map<String, String> {
        val allEntries = preferences.all
        val map = mutableMapOf<String, String>()
        for ((k, v) in allEntries) {
            if (k.startsWith("$key.")) {
                map[k.removePrefix("$key.")] = v as String
            }
        }
        Napier.d("Android: Retrieved Map '$key' = '$map'")
        return map
    }


    actual fun clearPreferences(key: String?) {
        if (!key.isNullOrEmpty()) {
            preferences.edit().remove(key).apply()
            Napier.d { "${key} Preference cleared" }
        } else {
            preferences.edit().clear().apply()
            Napier.d("All Preference cleared")
        }

    }

}