package com.stephben.hypewear.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun ProfileCircle(
    modifier: Modifier = Modifier,
    imageUrl: String
) {


    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(enable = true)
            .build(),
        contentDescription = "Example content desc",
        contentScale = ContentScale.Crop,
        error = {
            Image(
                painterResource(id = R.drawable.hypewear_logo_nobg_zoom),
                contentDescription = "Circular Image",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(CircleShape)
                    .border(1.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
            )
        },
        modifier = modifier
            .clip(CircleShape)
            .border(2.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
    )
}


@Preview
@Composable
private fun ProfileCirclePrev() {
    HypeWearTheme {
        ProfileCircle(imageUrl = "")
    }
}