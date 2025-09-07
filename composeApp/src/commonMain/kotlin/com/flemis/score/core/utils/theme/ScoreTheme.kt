package com.flemis.score.core.utils.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


private val lightColorPalette = lightColorScheme(
    surface = Color.Black,
)

@Composable
private fun lightTypography() = Typography()

private val darkColorPalette = darkColorScheme(
    surface = Color.Black,

    )

@Composable
private fun darkTypography() = Typography()

@Composable
fun ScoreTheme(isDarkMode: Boolean, content: @Composable () -> Unit) {
    val colors = if (isDarkMode) darkColorPalette else lightColorPalette
    val typos = if (isDarkMode) darkTypography() else lightTypography()
    MaterialTheme(typography = typos, content = content, colorScheme = colors)
}