package com.stephben.hypewear.order.presentation.order_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.order.presentation.order_list.components.ExpandableCard
import com.stephben.hypewear.order.presentation.order_list.components.OrderListHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderListScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderListViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.onAction(OrderListAction.OnLoadOrders)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp)
    ) {
        OrderListHeader(
            modifier = Modifier.padding(top = 32.dp).align(Alignment.TopCenter),
            onBackClick = {onBackClick()}
        )

        Column(
            modifier = modifier.fillMaxWidth().padding(top = 80.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                return
            }

            if (state.orders.isEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No available orders",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )
                return
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.orders) { order ->
                    state.orderApparels[order.orderId]?.let { ExpandableCard(order = order, apparels = it) }
                }
            }
        }
    }
}