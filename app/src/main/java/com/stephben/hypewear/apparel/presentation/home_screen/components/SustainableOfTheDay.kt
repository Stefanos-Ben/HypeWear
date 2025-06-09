package com.stephben.hypewear.apparel.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.stephben.hypewear.R
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun SustainableOfTheDay(
    apparel: Apparel
) {
    Box(
        modifier = Modifier
            .size(300.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(apparel.imageUrl)
                .crossfade(enable = true)
                .build(),
            contentDescription = "Example content desc",
            contentScale = ContentScale.Crop,
            error = {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,

                )
            },
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(30.dp))
                .padding(horizontal = 1.dp)
        )

        Image(
            painter = painterResource(R.drawable.most_sustainable),
            contentDescription = null,
            modifier =
                Modifier.fillMaxSize().padding(vertical = 3.5.dp)
        )

    }
}

@Preview
@Composable
private fun SustainableOfTheDayPrev() {
    HypeWearTheme {
        SustainableOfTheDay(apparel = Apparel(
            imageUrl = "https://www.sneaker10.gr/3039753-product_large/puma-sds-relaxed-graphic-track-jacket-wv.jpg"
        ))
    }
}