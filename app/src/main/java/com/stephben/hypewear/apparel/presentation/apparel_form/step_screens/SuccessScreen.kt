package com.stephben.hypewear.apparel.presentation.apparel_form.step_screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.R
import kotlinx.coroutines.delay

@Composable
fun SuccessScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3_600)
        onDone()
    }

    var startAnim by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0f,
        animationSpec = keyframes {
            durationMillis = 1_800
            0.0f at 0
            1.15f at 950 using LinearOutSlowInEasing
            1.00f at 1_800 using FastOutSlowInEasing
        },
        label = "popScale"
    )

    val alpha by animateFloatAsState(
        targetValue = if  (startAnim) 1f else 0f,
        animationSpec = tween(800),
        label = "fadeIn"
    )

    LaunchedEffect(true) { startAnim = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(R.drawable.apparel_success),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .graphicsLayer {
                        this.scaleX = scale
                        this.scaleY = scale
                        this.alpha = alpha
                    }
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Apparel saved!",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.graphicsLayer { this.alpha = alpha }
            )
        }
    }
}