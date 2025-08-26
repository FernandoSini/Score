package com.flemis.score

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.flemis.score.core.di.androidModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                detectDarkMode = { resources -> true }),
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                detectDarkMode = { resources -> true })
        )
        super.onCreate(savedInstanceState)
        loadKoinModules(androidModule(this))
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(
            androidModule(this@MainActivity)
        )
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

