package com.flemis.score.core.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import androidx.core.os.LocaleListCompat


class AppLanguageAndroid(private val context: Context) {

    fun setLocale(code: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService<LocaleManager>()?.applicationLocales = LocaleList.forLanguageTags(code)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
        }
    }

    fun getLocale(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return context.getSystemService<LocaleManager>()?.getApplicationLocales(context.packageName)?.get(0)
                ?.toLanguageTag()
                ?: "en-US"
        } else {
            val locale = AppCompatDelegate.getApplicationLocales()
            return context.resources.configuration.locales.get(0).toLanguageTag() ?: "en-US"
        }
    }
}

@Composable
actual fun rememberLocale(): String {
    val context = LocalContext.current
    val language = AppLanguageAndroid(context).getLocale()

    return remember(language) {
        language
    }
}