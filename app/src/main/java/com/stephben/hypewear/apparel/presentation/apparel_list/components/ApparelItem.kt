package com.stephben.hypewear.apparel.presentation.apparel_list.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.stephben.hypewear.R
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@SuppressLint("DefaultLocale")
@Composable
fun ApparelItem(
    apparel: Apparel,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(4.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
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
                        painter = painterResource(R.drawable.freepik__adjust__90022),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .height(400.dp)

            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = apparel.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = apparel.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = String.format("%.2f", apparel.price) +  apparel.currency,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(48.dp))
        }

    }
}

@Preview(
    name = "Apparel Item Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Apparel Item Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF100E07
)
@Composable
private fun ApparelItemPreview() {
    HypeWearTheme {
        ApparelItem(
            apparel = Apparel(
                title = "WhiteWorks SA",
                description = "Your go-to white Eco friendly t-shirt",
                price = 24.00
            )
        ) { }
    }
}