package com.flemis.score.features.base.presentation.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.crossfade
import com.flemis.score.core.utils.toNormalDate
import io.github.aakira.napier.Napier
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.coringa
import score.composeapp.generated.resources.soccer_bg
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun SportsCardScore(
    minBoxHeight: Dp = 200.dp, maxBoxHeight: Dp = 400.dp, minWidth: Dp = 20.dp, maxWidth: Dp = 450.dp
) {
    val teamImage = rememberAsyncImagePainter(
        "", imageLoader = ImageLoader.Builder(LocalPlatformContext.current)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    )
    val imageState = teamImage.state.collectAsState();

    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    BoxWithConstraints(
        modifier = Modifier.padding(horizontal = 15.dp).heightIn(min = minBoxHeight, max = maxBoxHeight)
            .widthIn(max = maxWidth, min = minWidth), propagateMinConstraints = false, content = {
            Card(
                modifier = Modifier.size(height = minHeight, width = maxWidth),
                elevation = CardDefaults.elevatedCardElevation(0.dp),
                content = {
                    Box(Modifier.fillMaxSize(), content = {
                        Column(
                            Modifier.fillMaxSize().padding(top = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content = {

                                Row(
                                    Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Row(
                                            horizontalArrangement = spacedBy(10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            content = {
                                                renderClubLogo(imageState.value)
                                                Text(
                                                    "São paulo",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp
                                                )
                                            })
                                        Text(
                                            "203",
                                            fontSize = 20.sp,
                                            color = Color(0xff008000),
                                            modifier = Modifier
                                                .size(height = 50.dp, width = 80.dp)
                                                .padding(8.dp)
                                                .background(
                                                    Color(0xffa1a1a1).copy(alpha = 0.3f),
                                                    shape = RoundedCornerShape(10.dp)
                                                )
                                                .wrapContentHeight(Alignment.CenterVertically)
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                        )
                                    })
                                Row(
                                    Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Row(
                                            horizontalArrangement = spacedBy(10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            content = {
                                                renderClubLogo(imageState.value)
                                                Text(
                                                    "São paulo",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp
                                                )
                                            })
                                        Text(
                                            "203",
                                            fontSize = 20.sp,
                                            color = Color(0xff008000),
                                            modifier = Modifier
                                                .size(height = 50.dp, width = 80.dp)
                                                .padding(8.dp)
                                                .background(
                                                    Color(0xffa1a1a1).copy(alpha = 0.3f),
                                                    shape = RoundedCornerShape(10.dp)
                                                )
                                                .wrapContentHeight(Alignment.CenterVertically)
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                        )
                                    })
                            })
                        Text(
                            currentDate.toNormalDate(),
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.TopStart).padding(start = 15.dp, top = 15.dp),
                        )
                    })
                })
        })
}

@Composable
private fun renderClubLogo(state: AsyncImagePainter.State): Any {
    when (state) {
        is AsyncImagePainter.State.Error, AsyncImagePainter.State.Empty -> {
            if (state is AsyncImagePainter.State.Error) {
                Napier.e {
                    "erro ao carregar logo do clube: " +
                            state.result.throwable.message.toString()
                }
            }
            return Box(
                Modifier.size(50.dp),
                contentAlignment = Alignment.Center,
                content = {
                Image(
                    painter = painterResource(Res.drawable.coringa),
                    contentDescription = "club_logo_error",
                    Modifier.size(25.dp),
                    colorFilter = ColorFilter.tint(Color.Black)

                )
            })
        }

        is AsyncImagePainter.State.Loading -> {

            return Box(
                Modifier.size(50.dp),
                contentAlignment = Alignment.Center,
                content = {
                CircularProgressIndicator()
            })
        }

        is AsyncImagePainter.State.Success -> {
            return AsyncImage(
                model = state.result.request.data,
                modifier = Modifier.size(50.dp),
                clipToBounds = true,
                contentScale = ContentScale.FillBounds,
                contentDescription = "club_logo_success"

            )
        }

    }
}

