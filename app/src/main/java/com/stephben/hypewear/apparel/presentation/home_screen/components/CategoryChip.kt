package com.stephben.hypewear.apparel.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.R
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun CategoryChip(
    category: String,
    onCategoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(130.dp)
            .clickable {
                onCategoryClick()
            }
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(90.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        Image(
            painter = when (category) {
                "T-shirt" -> painterResource(R.drawable.category_tshirt)
                "Hoodie" -> painterResource(R.drawable.category_hoodie)
                "Sweatshirt" -> painterResource(R.drawable.category_sweatshirt)
                "Jacket" -> painterResource(R.drawable.category_jacket)
                "Jeans" -> painterResource(R.drawable.category_jeans)
                "Trousers" -> painterResource(R.drawable.category_trousers)
                "Shorts" -> painterResource(R.drawable.category_shorts)
                "Dress" -> painterResource(R.drawable.category_dress)
                "Skirt" -> painterResource(R.drawable.category_skirt)
                "Sneakers" -> painterResource(R.drawable.category_sneakers)
                "Boots" -> painterResource(R.drawable.category_boots)
                "Accessories" -> painterResource(R.drawable.category_accessories)
                else -> painterResource(R.drawable.error_24)
            },
            contentDescription = "Category chip",
            contentScale = ContentScale.Fit,
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .size(90.dp)
        )

        Text(
            text = category,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Preview
@Composable
private fun CategoryChipPrev() {
    HypeWearTheme {
        CategoryChip(
            category = "Dress",
            onCategoryClick = {}
        )
    }
}