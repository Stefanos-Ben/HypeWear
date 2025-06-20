package com.stephben.hypewear.order.presentation.order_list.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme
import com.stephben.hypewear.order.domain.Order
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun ExpandableCard(
    order: Order,
    apparels: List<Apparel>
) {
    var expandedState by remember { mutableStateOf(false) }
    val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm")
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = ShapeDefaults.Medium,
        onClick = { expandedState = !expandedState }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = order.createdAt?.let { formatter.format(it) } ?: "Undefined",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )



                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = { expandedState = !expandedState }
                ) {
                    Icon(
                        modifier = Modifier.weight(1f),
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }

            if (expandedState) {
                LazyColumn(
                    modifier = Modifier.height(200.dp),
                ) {
                    item {
                        Text(
                            modifier = Modifier.weight(2f),
                            text = "Order ID: ${order.orderId}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(apparels) { apparel ->
                        val orderItem = order.items.find { it.apparelId == apparel.apparelID }
                        Column {
                            Text(
                                text = apparel.description,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            if (orderItem != null) {
                                Text(
                                    text = "${orderItem.size} ~ x ${orderItem.quantity} ~ ${orderItem.price} €",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun ExpandableCardPrev() {
    HypeWearTheme {
        ExpandableCard(order = Order(), apparels = emptyList())
    }
}