package com.flemis.score.features.base.presentation.pages

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flemis.score.features.base.domain.entities.SportType
import com.flemis.score.features.base.presentation.ui.viewmodel.SportsMenuViewModel
import com.flemis.score.features.base.presentation.ui.widgets.CustomTopAppbar
import com.flemis.score.features.base.presentation.ui.widgets.SportsCardScore
import com.flemis.score.features.base.presentation.ui.widgets.SportsMenu
import com.flemis.score.features.base.presentation.ui.widgets.SportsVideoCardItem
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.soccer_appbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen() {
    val menuViewmodel = koinViewModel<SportsMenuViewModel>()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppbar(appbarMaxHeight = 300.dp, appbarMinHeight = 100.dp, menuItems = null)

        },
        content = {
            Surface(
                color = Color.Black,
                modifier = Modifier.fillMaxSize(),
            ) {
                FlowColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()).verticalScroll(
                            rememberScrollState(),
                            flingBehavior = ScrollableDefaults.flingBehavior(),
                            overscrollEffect = LocalOverscrollFactory.current?.createOverscrollEffect()
                        ),
                    //   verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically),
                    content = {
                        SportsMenu(SportType.entries, menuViewmodel)
                        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                            SportsVideoCardItem()
                            SportsCardScore()
                        }
                    }
                )
            }
        },
    )

}


