package com.stephben.hypewear.apparel.presentation.apparel_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme


@Composable
fun ApparelItemButtons(
    onCartClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .clip(RoundedCornerShape(
                topStartPercent = 30,
                topEndPercent = 30,
                bottomStartPercent = 100,
                bottomEndPercent = 100))
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){

        IconButton(
            onClick = onCartClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(4.dp)
                .clip(CircleShape)
                .size(38.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Add to Cart",
            )
        }

        IconButton(
            onClick = onFavoriteClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(4.dp)
                .clip(CircleShape)
                .size(35.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "Add to Cart",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}




@Preview
@Composable
private fun ApparelItemButtonsPrev() {
    HypeWearTheme { ApparelItemButtons(
        onCartClick = {},
        onFavoriteClick = {}
    ) }
}