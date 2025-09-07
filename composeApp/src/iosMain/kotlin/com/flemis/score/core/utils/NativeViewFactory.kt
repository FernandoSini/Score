package com.flemis.score.core.utils

import platform.UIKit.UIViewController

interface NativeViewFactory {
    fun openModal(title: String, onClick: () -> Unit): UIViewController
}