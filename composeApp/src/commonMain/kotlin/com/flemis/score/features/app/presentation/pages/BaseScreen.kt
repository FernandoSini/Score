package com.flemis.score.features.app.presentation.pages

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flemis.score.features.app.presentation.navigation.Routes
import com.flemis.score.features.app.presentation.ui.viewmodel.AppViewModel
import com.flemis.score.features.home.presentation.pages.HomeScreen
import com.flemis.score.features.home.presentation.ui.viewmodel.SportsMenuViewModel
import com.flemis.score.features.home.presentation.ui.widgets.CustomTopAppbar
import com.flemis.score.features.teams.presentation.pages.TeamsScreen
import com.flemis.score.getPlatform
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen() {
    val sportsMenuViewModel = koinViewModel<SportsMenuViewModel>()
    val appViewModel = koinViewModel<AppViewModel>()
    val navController = rememberNavController()
    val currentDestination by navController.currentBackStackEntryAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize().padding(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            CustomTopAppbar(
                appbarMaxHeight = 300.dp,
                appbarMinHeight = 100.dp,
                navController = navController,
                hasPreviousRoute = false,
                customContent = {
                    Column(
                        modifier = Modifier.padding(start = 15.dp),
                        verticalArrangement = Arrangement.Center,
                        content = {
                            Text(
                                "Welcome,", color = Color.White, style = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.W600,
                                )
                            )
                            Text(
                                "Fernando", color = Color.White,
                                fontSize = 30.sp,

                                )
                        }
                    )
                })

        },
        content = {
            appViewModel.homeMenuItemsState.value.map { element ->
                renderScreens(
                    route = element["screen"].toString(),
                    navController = navController,
                    sportsMenuViewModel = sportsMenuViewModel,
                    paddingValues = it

                )
            }

        },
        bottomBar = {
            Column {
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), 0.2.dp, color = Color.Gray)
                BottomAppBar(
                    modifier = if (getPlatform().name.lowercase()
                            .contains("ios")
                    ) Modifier.padding(bottom = 0.dp)
                    else Modifier,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = Color.White,
                    content = {
                        appViewModel.homeMenuItemsState.value.mapIndexed { index, element ->
                            NavigationBarItem(
                                //selected = currentDestination?.destination?.hierarchy?.any { it.route == element["screen"].toString() } == true,
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent,
                                    selectedIconColor = MaterialTheme.colorScheme.surfaceTint,
                                    unselectedIconColor = MaterialTheme.colorScheme.inverseSurface,
                                    selectedTextColor = MaterialTheme.colorScheme.surfaceTint,

                                    ),
                                interactionSource = remember { MutableInteractionSource() },
                                label = { Text(stringResource(element["label"] as StringResource)) },
                                icon = {
                                    renderBottomNavItem(element["icon"])
                                },
                                selected = appViewModel.selectedIndexState.collectAsState().value == index,
                                onClick = {
                                    appViewModel.selectIndex(index)
                                    navController.navigate(route = element["screen"].toString()) {
                                        popUpTo(currentDestination?.destination?.route.toString()) {
                                            //saveState = true
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                    },
                )
            }
        }
    )

}

@Composable
private fun renderBottomNavItem(element: Any?) {
    when (element) {
        is ImageVector -> {
            return Icon(imageVector = element, contentDescription = "bottom_nav_vector")
        }

        is DrawableResource -> {
            return Icon(painter = painterResource(element), contentDescription = "bottom_nav_painter")
        }

        else -> {}
    }
}

@Composable
private fun renderScreens(
    route: String,
    sportsMenuViewModel: SportsMenuViewModel,
    navController: NavHostController, paddingValues: PaddingValues,
) {
    return NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        enterTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(500)
            )
        }

    ) {

        composable("home") {
            HomeScreen(sportsMenuViewModel, paddingValues)
        }
        composable("teams") {
            TeamsScreen(paddingValues)
        }
    }
}


