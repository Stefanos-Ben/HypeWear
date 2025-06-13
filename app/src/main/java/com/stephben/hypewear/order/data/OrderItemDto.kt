package com.stephben.hypewear.order.data

data class OrderItemDto(
    val apparelId: String? = null,
    val size: String? = null,
    val userId: String? = null,
    val quantity: Int? = null,
    val price: Double? = null,
    val brandId: String? = null
)
