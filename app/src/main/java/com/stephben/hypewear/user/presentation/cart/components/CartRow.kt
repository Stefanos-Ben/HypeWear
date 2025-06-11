package com.stephben.hypewear.user.presentation.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.stephben.hypewear.R
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme
import com.stephben.hypewear.user.domain.Cart

@Composable
fun CartRow(
    modifier: Modifier = Modifier,
    cartItem: Cart,
    apparel: Apparel,
    onSizeChange: (String) -> Unit,
    onQuantityAdd: () -> Unit,
    onQuantitySubtract: () -> Unit,
    onRemoveFromCart: () -> Unit,
) {

    val sizes = remember { apparel.stockPerSize.keys.toList() }


    Card(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(apparel.imageUrl)
                    .crossfade(enable = true)
                    .build(),
                contentDescription = "Apparel image",
                contentScale = ContentScale.Crop,
                error = {
                    Image(
                        painter = painterResource(R.drawable.hypewear_logo_nobg_zoom),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(64.dp)
                    )
                },
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = apparel.description,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                CartRowSize(
                    sizes = sizes,
                    currentSize = cartItem.size,
                    onSizeChange = { size -> onSizeChange(size) }
                )

                CartRowQuantity(
                    quantity = cartItem.quantity,
                    onQuantityAdd = onQuantityAdd,
                    onQuantitySubtract = onQuantitySubtract
                )
            }

            IconButton(onClick = {onRemoveFromCart()}) {
                Icon(
                    painter = painterResource(R.drawable.delete_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CartRowPrev() {
    HypeWearTheme {
        CartRow(
            cartItem = Cart(size = "M", quantity = 1),
            apparel = Apparel(description = "Blouse t-shirt"),
            onSizeChange = {},
            onQuantityAdd = {},
            onQuantitySubtract = {},
            onRemoveFromCart = {}
        )
    }
}