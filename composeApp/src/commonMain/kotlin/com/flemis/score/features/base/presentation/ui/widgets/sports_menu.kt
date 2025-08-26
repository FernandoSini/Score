package com.flemis.score.features.base.presentation.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.flemis.score.features.base.domain.entities.SportType
import com.flemis.score.features.base.presentation.ui.viewmodel.SportsMenuViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue


@Composable
fun SportsMenu(
    items: List<SportType>,
    sportsMenuViewModel: SportsMenuViewModel,
    modifier: Modifier = Modifier.padding(vertical = 20.dp).heightIn(50.dp, max = 100.dp).fillMaxWidth()
) {
    val selectedSport by sportsMenuViewModel.selectedSportState.collectAsState()
    BoxWithConstraints(
        modifier, contentAlignment = Alignment.Center,
        propagateMinConstraints = true,
        content = {
            LazyRow(
                modifier = Modifier,
                userScrollEnabled = true,
                flingBehavior = ScrollableDefaults.flingBehavior(),
                state = rememberLazyListState(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(start = 50.dp, end = 50.dp),
                content = {
                    items(items.size) { index ->
                        Box(
                            Modifier.clip(CircleShape)
                                .height(50.dp)
                                .width(50.dp)
                                .background(Color(0xffa1a1a1))
                                .clickable(
                                    onClick = {
                                        sportsMenuViewModel.viewModelScope.launch {
                                            sportsMenuViewModel.changeSport(items[index])
                                        }
                                    }),
                            contentAlignment = Alignment.Center,

                            content = {
                                Icon(
                                    items[index].icon,
                                    modifier = Modifier.size(20.dp),
                                    contentDescription = "menu_icon",
                                    tint = if (selectedSport == items[index]) Color.Red else Color.White
                                )
                            },
                        )
                    }
                },
            )
        },
    )
}