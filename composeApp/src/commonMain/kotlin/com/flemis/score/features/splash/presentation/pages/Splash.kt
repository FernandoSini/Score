package com.flemis.score.features.splash.presentation.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.flemis.score.features.app.presentation.navigation.Routes
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.soccer_bg
import kotlin.time.Duration.Companion.seconds

@Composable
fun SplashScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val currentRoute by navController.currentBackStackEntryAsState()

    LifecycleStartEffect(Unit, lifecycleOwner = LocalLifecycleOwner.current) {
        scope.launch {
            delay(5.seconds)
            navController.navigate(Routes.BASE_ROUTE) {
                popUpTo(currentRoute?.destination?.route.toString()) {
                    inclusive = true
                }
            }

        }
        onStopOrDispose { scope.cancel() }

    }

    Scaffold(
        Modifier.fillMaxSize(),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top)
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.soccer_bg),
                contentDescription = "splash_background",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize().blur(5.dp, BlurredEdgeTreatment.Rectangle),
            )
        }

    }
}