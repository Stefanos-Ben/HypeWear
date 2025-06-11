package com.stephben.hypewear.user.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
    //onGoToCheckOut: () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

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
            Column(modifier = Modifier.fillMaxSize()) {
                CartHeader(modifier = Modifier.padding(top = 40.dp))

                Spacer(modifier = Modifier.height(36.dp))

                LazyColumn(modifier = Modifier.padding(16.dp)) {
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
            }
        }
        Box(modifier =  Modifier.align(Alignment.BottomStart)){
            bottomBar()
        }
    }
}