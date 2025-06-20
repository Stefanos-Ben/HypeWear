package com.stephben.hypewear.order.presentation.order_list

sealed interface OrderListAction {
    data object OnLoadOrders: OrderListAction
}