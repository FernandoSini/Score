package com.flemis.score

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController
import com.flemis.score.core.di.initKoin
import com.flemis.score.core.utils.NativeViewFactory
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import platform.UIKit.UIView

val LocalNativeViewFactory = staticCompositionLocalOf<NativeViewFactory> {
    error("No factory Found")
}


@OptIn(ExperimentalComposeApi::class, ExperimentalComposeUiApi::class)
fun MainViewController(nativeViewFactory: NativeViewFactory) = ComposeUIViewController(
    configure = {
        opaque = true
        initKoin()
        enforceStrictPlistSanityCheck = false
     //   parallelRendering =true
    }

) {
    Napier.base(DebugAntilog(coroutinesSuffix = false, defaultTag = "Score"))
    CompositionLocalProvider(LocalNativeViewFactory provides nativeViewFactory) {
        App()

    }

}