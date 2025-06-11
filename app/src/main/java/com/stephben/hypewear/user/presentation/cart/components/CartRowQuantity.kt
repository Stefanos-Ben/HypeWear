package com.stephben.hypewear.user.presentation.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.R

@Composable
fun CartRowQuantity(
    quantity: Int,
    onQuantityAdd: () -> Unit,
    onQuantitySubtract: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically){
        IconButton(onClick = { onQuantitySubtract() }) {
            Icon(
                painter = painterResource(R.drawable.remove_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )
        }
        Text(
            text = quantity.toString(),
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(onClick = { onQuantityAdd() }) {
            Icon(
                painter = painterResource(R.drawable.add_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )
        }
    }
}