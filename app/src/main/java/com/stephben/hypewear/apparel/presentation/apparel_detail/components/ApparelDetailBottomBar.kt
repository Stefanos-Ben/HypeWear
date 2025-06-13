package com.stephben.hypewear.apparel.presentation.apparel_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.R

@Composable
fun ApparelDetailBottomBar(
    modifier: Modifier = Modifier,
    price: String,
    quantity: Int,
    onQuantityAdd: () -> Unit,
    onQuantitySubtract: () -> Unit,
    onCartClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text =  price,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            fontSize = 27.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        if (quantity > 0) {
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
                    color = MaterialTheme.colorScheme.onBackground,
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
        } else {
            Button(
                onClick = onCartClick,
                modifier = Modifier.height(55.dp).width(140.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp
                ),
            ) {
                Text(
                    text = "Add to Cart",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.bodyMedium

                )
            }
        }

    }
}