package com.flemis.score.core.utils

import io.github.aakira.napier.Napier
import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import platform.Foundation.NSMutableDictionary
import platform.Foundation.NSString
import platform.Foundation.NSUserDefaults

actual class MultiPlatformPreferences {

    private val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()
    actual fun putString(key: String, value: String) {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        userDefaults.setObject(value, key)
        userDefaults.synchronize()
        Napier.d { "iOS: Saved String '$key' = '$value'" }
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        val value = userDefaults.stringForKey(key)
        Napier.d { "iOS: Retrieved String '$key' = '$value'" }
        return value
    }

    actual fun putInt(key: String, value: Int) {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        userDefaults.setInteger(value.toLong(), key)
        userDefaults.synchronize()
        Napier.d { "iOS: Saved Int '$key' = '$value'" }

    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        val value = userDefaults.integerForKey(key)
        Napier.d { "iOS: Retrieved Int '$key' = '$value'" }
        return value.toInt()
    }

    actual fun putBoolean(key: String, value: Boolean) {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        userDefaults.setBool(value, key)
        userDefaults.synchronize()
        Napier.d { "iOS: Saved Boolean '$key' = '$value'" }
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        val value = userDefaults.boolForKey(key)
        Napier.d { "iOS: Retrieved Bool '$key' = '$value'" }
        return value
    }

    actual fun putFloat(key: String, value: Float) {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        userDefaults.setFloat(value, key)
        userDefaults.synchronize()
        Napier.d { "iOS: Saved Float '$key' = '$value'" }
    }

    actual fun getFloat(key: String, defaultValue: Float): Float {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        val value = userDefaults.floatForKey(key)

        Napier.d { "iOS: Retrieved Float '$key' = '$value'" }
        return value
    }

    actual fun putLong(key: String, value: Long) {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        userDefaults.setInteger(value, key)
        userDefaults.synchronize()
        Napier.d { "iOS: Saved Long '$key' = '$value'" }
    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        val value = userDefaults.integerForKey(key)
        Napier.d { "iOS: Retrieved Long '$key' = '$value'" }
        return value.toLong()
    }

    actual fun putStringSet(key: String, value: Set<String>) {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        userDefaults.setObject(value.toList(), key)
        Napier.d { "iOS: Saved List '$key' = '$value'" }
    }

    @Suppress("UNCHECKED_CAST")
    actual fun getStringSet(key: String, defaultValue: Set<String>): Set<String?>? {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        val value = userDefaults.objectForKey(key) as List<String?> ?: return null
        Napier.d { "iOS: Retrieved List '$key' = '$value'" }
        return value?.toSet()

    }

    actual fun putMap(key: String, value: Map<String, String>) {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        val dict = NSMutableDictionary()
        for ((k, v) in value) {
            dict.setObject(v as NSString, k as NSString)
        }
        userDefaults.setObject(dict, key)
        userDefaults.synchronize()
        Napier.d { "iOS: Saved Map '$key' = '$dict'" }
    }

    actual fun getMap(key: String): Map<String, String> {
        userDefaults.persistentDomainForName(NSBundle.mainBundle.bundleIdentifier ?: "")
        val dict = userDefaults.objectForKey(key) as NSDictionary
        val result = mutableMapOf<String, String>()
        dict?.let {
            val enumerator = it.keyEnumerator()
            while (true) {
                val nsKey = enumerator.nextObject() as NSString ?: break
                val nsValue = it.objectForKey(nsKey) as? NSString
                result[nsKey.toString()] = nsValue?.toString() ?: ""
            }

        }
        return result
    }


    actual fun clearPreferences(key: String?) {
        if (key.isNullOrEmpty()) {
            NSUserDefaults.resetStandardUserDefaults()
            userDefaults.dictionaryRepresentation().forEach { k ->
                userDefaults.removeObjectForKey(k as String)
            }
            userDefaults.synchronize()
            Napier.d { "All ios preferences cleared" }
        } else {
            userDefaults.removeObjectForKey(key)
            userDefaults.synchronize()
            Napier.d{ "$key ios preference cleared" }
        }
    }

}