package com.flemis.score

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.AnimationScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import com.flemis.score.features.app.data.datasource.local.db.AppDatabase
import com.flemis.score.features.app.presentation.navigation.Routes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.core.Koin
import androidx.navigation.compose.composable
import com.flemis.score.features.base.presentation.pages.BaseScreen
import com.flemis.score.features.splash.presentation.pages.SplashScreen
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val navigator = rememberNavController()
        Scaffold(
            content = {
                NavHost(
                    navigator, modifier = Modifier,
                    startDestination = Routes.SPLASH_ROUTE,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popEnterTransition = { slideInHorizontally(tween(700)) },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End,
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