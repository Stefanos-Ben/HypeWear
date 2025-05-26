package com.stephben.hypewear.core.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.stephben.hypewear.R
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun BadgeDisplay(
    modifier: Modifier = Modifier,
    badge: String
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(
                when (badge) {
                    "Low-Carbon" -> R.drawable.low_carbon_badge
                    "Water-Smart" -> R.drawable.water_smart_badge
                    "Low-Impact Materials" -> R.drawable.low_impact_badge
                    "Plastic-Free Pack" -> R.drawable.plastic_free_pack_badge
                    else -> R.drawable.low_carbon_badge
                }
            ),
            contentDescription = badge,
            contentScale = ContentScale.Crop,
            modifier = Modifier.align(Alignment.Center)
        )
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
private fun BadgeDisplayPrev() {
    HypeWearTheme {
        BadgeDisplay(
            badge = "Low-Carbon"
        )
    }
}