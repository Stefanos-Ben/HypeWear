package com.stephben.hypewear.user.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.user.presentation.cart.components.CartHeader
import com.stephben.hypewear.user.presentation.cart.components.CartRow
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    bottomBar: @Composable () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val total = state.cartApparels.zip(state.userCart)
        .sumOf { (apparel, cartItem) ->
            apparel.price * cartItem.quantity
        }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp)
    ){
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.userCart.isEmpty()) {
            Text(
                text = "Your cart is empty",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                CartHeader(modifier = Modifier.padding(top = 40.dp, bottom = 56.dp))

                LazyColumn(modifier = Modifier.padding(8.dp).padding(bottom = 200.dp)) {
                    items(state.userCart) { cartItem ->
                        val apparel = state.cartApparels.find { it.apparelID == cartItem.apparelId }
                        apparel?.let {
                            CartRow(
                                cartItem = cartItem,
                                apparel = it,
                                onSizeChange = { size ->
                                    viewModel.onAction(CartAction.OnSizeChange(
                                        apparelId = it.apparelID, size = size
                                    ))
                                },
                                onQuantityAdd = {
                                    viewModel.onAction(CartAction.OnQuantityAdd(apparelId = it.apparelID))
                                },
                                onQuantitySubtract = {
                                    viewModel.onAction(CartAction.OnQuantitySubtract(apparelId = it.apparelID))
                                },
                                onRemoveFromCart = {
                                    viewModel.onAction(CartAction.OnRemoveFromCart(apparelId = it.apparelID))
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))
            }
            Box(
                modifier =  Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Total: $total ${state.cartApparels.firstOrNull()?.currency ?: "€"}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        onClick = { viewModel.onAction(CartAction.OnCheckout) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Checkout")
                        }
                    }
                }
            }
        }


        Box(modifier =  Modifier.align(Alignment.BottomStart)){
            bottomBar()
        }
    }
}