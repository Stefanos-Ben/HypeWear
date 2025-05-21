package com.stephben.hypewear.core.presentation.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.R
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = koinViewModel(),
    onNavigateToStart: (Boolean, Boolean, String?) -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(state.isLoading) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 750,
                easing = {
                    OvershootInterpolator(0.5f).getInterpolation(it)
                }
            )
        )

        scale.animateTo(
            targetValue = 0.4f,
            animationSpec = tween(
                durationMillis = 750,
                easing = {
                    OvershootInterpolator(0.5f).getInterpolation(it)
                }
            )
        )

        scale.animateTo(
            targetValue = 0.6f,
            animationSpec = tween(
                durationMillis = 750,
                easing = {
                    OvershootInterpolator(0.5f).getInterpolation(it)
                }
            )
        )
        delay(100L)
        if (!state.isLoggedIn || state.userType != null) {
            onNavigateToStart(state.isLoggedIn, state.isEmailVerified, state.userType)
        }

    }

    Box(
     modifier = Modifier
         .fillMaxSize()
         .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.hypewear_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .scale(scale.value)
        )
    }
}