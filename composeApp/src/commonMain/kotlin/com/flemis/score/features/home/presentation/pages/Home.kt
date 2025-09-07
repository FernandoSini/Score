package com.flemis.score.features.home.presentation.pages

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flemis.score.features.home.domain.entities.SportType
import com.flemis.score.features.home.presentation.ui.widgets.SportsVideoCardItem
import com.flemis.score.features.home.presentation.ui.viewmodel.SportsMenuViewModel
import com.flemis.score.features.home.presentation.ui.widgets.SportsCardScore
import com.flemis.score.features.home.presentation.ui.widgets.SportsMenu

@Composable
fun HomeScreen(sportsMenuViewModel: SportsMenuViewModel, paddingValues: PaddingValues) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxSize(),
                content = {
                    FlowColumn(
                        modifier = Modifier.fillMaxSize().padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                            .verticalScroll(
                                rememberScrollState(),
                                flingBehavior = ScrollableDefaults.flingBehavior(),
                                overscrollEffect = LocalOverscrollFactory.current?.createOverscrollEffect()
                            ),
                        content = {
                            SportsMenu(SportType.entries, sportsMenuViewModel)
                            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                                SportsVideoCardItem()
                                SportsCardScore()

                            }
                        }
                    )
                }
            )
        }
    )
}