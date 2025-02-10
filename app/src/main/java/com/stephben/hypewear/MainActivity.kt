package com.stephben.hypewear

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stephben.hypewear.apparel.presentation.apparel_list.ApparelListScreen
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HypeWearTheme {
                ApparelListScreen()
            }
        }
    }
}
