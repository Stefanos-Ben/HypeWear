package com.stephben.hypewear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.presentation.apparel_list.ApparelListScreen
import com.stephben.hypewear.apparel.presentation.components.ApparelItem
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

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
