package com.flemis.score.core.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.flemis.score.LocalNativeViewFactory

@Composable
actual fun nativeSheet(title: String, onClick: () -> Unit) {
    val factory = LocalNativeViewFactory.current
    UIKitViewController(
        modifier = Modifier.fillMaxSize(),
        factory = { factory.openModal(title, onClick) }
    )
}