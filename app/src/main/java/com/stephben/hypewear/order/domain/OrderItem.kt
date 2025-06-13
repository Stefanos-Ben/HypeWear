package com.stephben.hypewear.order.domain

data class OrderItem(
    val apparelId: String,
    val size: String,
    val quantity: Int,
    val price: Double,
    val brandId: String
)
