package com.stephben.hypewear.order.domain

import java.util.Date

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val items: List<OrderItem> = emptyList(),
    val total: Double = 0.0,
    val createdAt: Date? = null
)
