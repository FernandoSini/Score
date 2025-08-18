package com.flemis.score

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController
import com.flemis.score.core.di.initKoin

@OptIn(ExperimentalComposeApi::class, ExperimentalComposeUiApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        opaque = true
        initKoin()
        enforceStrictPlistSanityCheck = false
    }

) {
   // Napier.base(DebugAntilog(coroutinesSuffix = false, defaultTag = "Score"))
    App()
}