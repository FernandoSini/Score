package com.flemis.score.features.base.presentation.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import org.jetbrains.compose.resources.painterResource
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.soccer_appbar
import score.composeapp.generated.resources.soccer_bg

@Composable
fun SportsVideoCardItem(
    minBoxHeight: Dp = 200.dp,
    maxBoxHeight: Dp = 400.dp,
    minBoxWidth: Dp = 20.dp,
    maxBoxWidth: Dp = 450.dp,

    ) {
    BoxWithConstraints(
        Modifier.padding(horizontal = 15.dp).clip(RoundedCornerShape(15.dp))
            .heightIn(min = minBoxHeight, max = maxBoxHeight).widthIn(minBoxWidth, maxBoxWidth),
        propagateMinConstraints = false,
        content = {

            Card(modifier = Modifier.height(minHeight).width(maxWidth), content = {
                Box(Modifier.fillMaxSize(), content = {
                    Image(
                        painter = painterResource(Res.drawable.soccer_bg),
                        contentDescription = "card_bg",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    Column(
                        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally, content = {
                            Text("teste teste", color = Color.White)
                            Box(
                                modifier = Modifier, contentAlignment = Alignment.Center, content = {
                                    Box(
                                        Modifier.clip(CircleShape)
                                            .size(50.dp).blur(
                                                40.dp,
                                                BlurredEdgeTreatment.Unbounded
                                            )
                                            .background(Color(0xffa1a1a1).copy(alpha = 0.5f)),
                                        contentAlignment = Alignment.Center,
                                        content = {

                                        }
                                    )

                                    IconButton(
                                        modifier = Modifier.size(50.dp),
                                        shape = CircleShape,
                                        onClick = {},
                                        content = {
                                            Icon(
                                                Icons.Filled.PlayArrow,
                                                contentDescription = "card_play_button",
                                                tint = Color.White,
                                                modifier = Modifier.size(30.dp)

                                            )
                                        })
                                })
                            Text("teste teste", color = Color.White)

                        })

                })
            })

        },
    )
}