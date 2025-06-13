package com.stephben.hypewear.order.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class OrderDto(
    @DocumentId
    val orderId: String? = null,
    val userId: String? = null,
    val brandIds:  List<String>? = null,
    val items: List<OrderItemDto>? = null,
    val total: Double? = null,
    @ServerTimestamp
    val createdAt: Date? = null
)