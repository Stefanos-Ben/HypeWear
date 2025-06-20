package com.stephben.hypewear.order.presentation.order_list

import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.order.domain.Order

data class OrderListState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val orderApparels: Map<String, List<Apparel>> = emptyMap()
)
