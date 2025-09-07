package com.flemis.score.features.teams.presentation.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.flemis.score.core.utils.nativeSheet
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.coringa


@Composable
fun TeamsScreen(paddingValues: PaddingValues) {
    Scaffold(
        Modifier.fillMaxSize(),

        containerColor = MaterialTheme.colorScheme.background,
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                content = {
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxSize().padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        ),
                        content = {
                            TeamGrid()

                            nativeSheet("teste", {})
                        }
                    )
                }
            )

        },
    )

}

@Composable
fun TeamGrid(
    gridSize: Dp = 80.dp, selectedTeam: Int = 2
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(gridSize),
        verticalItemSpacing = 5.dp,
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxSize().padding(vertical = 20.dp),
    ) {
        items(10) { index ->
            Box(
                modifier = if (selectedTeam == index) Modifier.fillMaxSize()
                    .padding(8.dp) else Modifier.fillMaxSize()
                    .background(Color.Red.copy(alpha = 0.5f), shape = RoundedCornerShape(10.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Icon(
                            painter = painterResource(Res.drawable.coringa),
                            contentDescription = "team_icon",
                            modifier = Modifier.size(45.dp),
                            tint = MaterialTheme.colorScheme.surfaceTint,
                        )
                        Spacer(modifier = Modifier.heightIn(2.5.dp))
                        Text(
                            "club name",
                            autoSize = TextAutoSize.StepBased(
                                15.sp, 20.sp,
                                stepSize = 7.sp
                            ),
                            softWrap = true,
                            textAlign = TextAlign.Center,
                            //overflow = TextOverflow.Ellipsis,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                )
            }


        }
    }
}