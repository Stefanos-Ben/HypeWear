package com.stephben.hypewear.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme
import com.stephben.hypewear.navigation.RootGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HypeWearTheme {
                val navController = rememberNavController()
                RootGraph(navController = navController)
            }
        }
    }
}
