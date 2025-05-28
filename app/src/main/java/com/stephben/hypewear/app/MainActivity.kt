package com.stephben.hypewear.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.stephben.hypewear.core.data.DarkModePreferences
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme
import com.stephben.hypewear.navigation.RootGraph
import org.koin.compose.koinInject


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HypeWearRoot()
        }
    }
}

@Composable
fun HypeWearRoot(darkModePreferences: DarkModePreferences = koinInject()) {
    val isDark by darkModePreferences.darkModeEnabled.collectAsState(initial = false)

    HypeWearTheme(darkTheme = isDark) {
        val navController = rememberNavController()
        RootGraph(navController = navController)
    }
}