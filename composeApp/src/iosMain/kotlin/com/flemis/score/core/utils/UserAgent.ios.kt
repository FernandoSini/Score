package com.flemis.score.core.utils

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.UIKit.*
import platform.posix.*

class DeviceInfo {
    //eg. Darwin/16.3.0
    @OptIn(ExperimentalForeignApi::class)
    fun DarwinVersion(): String {
        val sysinfo = nativeHeap.alloc<utsname>()
        uname(sysinfo.ptr)

        val dv = sysinfo.release.toKString()
            .trim()
        nativeHeap.free(sysinfo)
        return "Darwin/$dv"

    }

    //eg. CFNetwork/808.3
    fun CFNetworkVersion(): String {
        val dictionary = NSBundle.bundleWithIdentifier("com.apple.CFNetwork")?.infoDictionary
        val version = dictionary?.get("CFBundleShortVersionString") as? String ?: "Unknown"
        return "CFNetwork/$version"
    }

    //eg. iOS/10_1
    fun deviceVersion(): String {
        var currentDevice = UIDevice.currentDevice
        return "\\${currentDevice.model} \\${currentDevice.systemName}\\${currentDevice.systemVersion}"
    }

    //eg. iPhone5,2
    @OptIn(ExperimentalForeignApi::class)
    fun deviceName(): String {
        var sysinfo = nativeHeap.alloc<utsname>()
        uname(sysinfo.ptr)
        val machine = sysinfo.machine.toKString()
        nativeHeap.free(sysinfo)
        return machine.trim()
    }

    //eg. MyApp/1
    fun appNameAndVersion(): String {
        val dictionary = NSBundle.mainBundle.infoDictionary ?: return ""
        val version = dictionary["CFBundleShortVersionString"] as? String ?: ""
        val name = dictionary["CFBundleName"] as? String ?: ""
        return "$name/$version"
    }

    fun getString(): String {
        return "User-Agent: \\${appNameAndVersion()} \\${deviceVersion()} \\${deviceName()} \\${CFNetworkVersion()} \\${DarwinVersion()}"
    }

    //print("the UA String is as \(UAString().addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!)")
}

actual class UserAgent {
    actual fun getUserAgent():String {
        return DeviceInfo().getString()
    }
}