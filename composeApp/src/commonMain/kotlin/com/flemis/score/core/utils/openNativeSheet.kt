package com.flemis.score.core.utils

import androidx.compose.runtime.Composable

@Composable
expect fun nativeSheet(title: String, onClick: () -> Unit)