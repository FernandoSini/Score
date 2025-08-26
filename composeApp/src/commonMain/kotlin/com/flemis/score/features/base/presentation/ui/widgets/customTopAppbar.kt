package com.flemis.score.features.base.presentation.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.painterResource
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.soccer_appbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppbar(appbarMinHeight: Dp = 100.dp, appbarMaxHeight: Dp = 300.dp, menuItems: List<Any>?) {

    BoxWithConstraints(
        Modifier.heightIn(min = appbarMinHeight, max = appbarMaxHeight).fillMaxWidth(),
        content = {
            val height = maxHeight - minHeight
            val maxWidth = maxWidth
            Napier.d { SportType.entries.first().name }
            Image(
                painterResource(Res.drawable.soccer_appbar),
                contentDescription = null,
                modifier = Modifier.size(height = maxHeight - minHeight, width = maxWidth)
                    .clip(RoundedCornerShape(bottomEnd = 100.dp)),
                contentScale = ContentScale.None,
            )
            CenterAlignedTopAppBar(
                title = { null },
                modifier = Modifier.size(height = height, width = maxWidth).clip(
                    RoundedCornerShape(bottomEnd = 100.dp)
                ),
                expandedHeight = height,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent, scrolledContainerColor = Color.Transparent
                ),
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.size(height = height, width = maxWidth)
                    ) {
                        IconButton(onClick = {}, modifier = Modifier, content = {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBackIos,
                                contentDescription = null,
                                tint = Color.White,
                            )
                        })
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {

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
                    }
                },


                )
            if (menuItems != null) {
                LazyRow(
                    state = rememberLazyListState(),
                    modifier = Modifier.overscroll(rememberOverscrollEffect()),
                    content = {

                    },
                )
            }
        },
    )

}