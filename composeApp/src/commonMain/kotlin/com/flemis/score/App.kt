package com.flemis.score

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import com.flemis.score.features.app.presentation.navigation.Routes
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import com.flemis.score.core.utils.theme.ScoreTheme
import com.flemis.score.features.app.presentation.pages.BaseScreen
import com.flemis.score.features.splash.presentation.pages.SplashScreen

@Composable
@Preview
fun App() {
    ScoreTheme(false) {
        var showContent by remember { mutableStateOf(false) }
        val navigator = rememberNavController()
        Scaffold(
            content = {
                NavHost(
                    navigator, modifier = Modifier,
                    startDestination = Routes.SPLASH_ROUTE,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popEnterTransition = { slideInHorizontally(tween(700)) },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    },

                    ) {

                    composable(Routes.BASE_ROUTE) {
                        BaseScreen()
                    }
                    composable(Routes.SPLASH_ROUTE) {
                        SplashScreen(navigator)
                    }
                }
            },

            )
    }
}