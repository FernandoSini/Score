package com.flemis.score.features.base.domain.entities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.outlined.SportsBaseball
import androidx.compose.material.icons.outlined.SportsBasketball
import androidx.compose.material.icons.outlined.SportsFootball
import androidx.compose.material.icons.outlined.SportsHockey
import androidx.compose.material.icons.outlined.SportsMotorsports
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material.icons.outlined.SportsVolleyball
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import score.composeapp.generated.resources.Res
import score.composeapp.generated.resources.hockey

enum class SportType(val int: Int, val icon: ImageVector) {
    soccer(1, Icons.Outlined.SportsSoccer),
    nfl(2, Icons.Outlined.SportsFootball),
    nba(3, Icons.Outlined.SportsBasketball),
    mlb(4, Icons.Outlined.SportsBaseball),
    f1(5, Icons.Outlined.SportsMotorsports),
    nhl(6, hockeyIcon),
    volley(7, Icons.Outlined.SportsVolleyball),
    handball(8, Icons.Default.SportsHandball)
}

val hockeyIcon = ImageVector.Builder(
    name = "Hockey",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 20f
).addPath(
    pathData = addPathNodes("M12,2.5C6.5,2.5 2,4.07 2,6C2,7.93 6.5,9.5 12,9.5C17.5,9.5 22,7.93 22,6C22,4.07 17.5,2.5 12,2.5ZM2,9.26V14C2,15.93 6.5,17.5 12,17.5C17.5,17.5 22,15.93 22,14V9.26C21.33,9.72 20.58,10.07 19.73,10.36C17.62,11.1 14.95,11.5 12,11.5C9.05,11.5 6.38,11.1 4.27,10.36C3.42,10.07 2.67,9.72 2,9.26Z"),
    fill = SolidColor(Color.Unspecified),//pra ficar com a cor cheia Colors.black
    stroke = SolidColor(Color.Black),
    strokeLineWidth = 1.5f,
).build()


