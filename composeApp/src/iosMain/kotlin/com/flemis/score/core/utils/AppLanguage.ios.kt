package com.flemis.score.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.setValue


 class AppLanguageIOS {
    fun setLocale(code:String){
     //   NSUserDefaults.standardUserDefaults.setObject(value = code, forKey = "AppLanguage")
       // NSUserDefaults.standardUserDefaults.synchronize()
        NSLocale.currentLocale.setValue(code, forKey = "AppLanguage")

    }
    fun getLocale():String{
        return NSLocale.currentLocale.languageCode
    }
}
@Composable
actual fun rememberLocale():String{
val nsLocale = NSLocale.currentLocale.languageCode;
    return remember(nsLocale){
        nsLocale
    }
}