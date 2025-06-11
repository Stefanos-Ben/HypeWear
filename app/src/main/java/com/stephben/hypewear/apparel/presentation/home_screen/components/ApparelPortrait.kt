package com.stephben.hypewear.apparel.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.stephben.hypewear.R

@Composable
fun ApparelPortrait(
    onClick: () -> Unit,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(topStartPercent = 30, topEndPercent = 30, bottomEndPercent = 10, bottomStartPercent = 10),
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxSize()
            .padding(bottom = 24.dp)

    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(enable = true)
                .build(),
            contentDescription = "Apparel Image",
            contentScale = ContentScale.Crop,
            error = {
                Image(
                    painter = painterResource(R.drawable.hypewear_logo_nobg_zoom),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(300.dp)
                )
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}