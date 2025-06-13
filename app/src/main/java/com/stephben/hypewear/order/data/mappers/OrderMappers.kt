package com.stephben.hypewear.order.data.mappers

import com.stephben.hypewear.order.data.OrderDto
import com.stephben.hypewear.order.data.OrderItemDto
import com.stephben.hypewear.order.domain.Order
import com.stephben.hypewear.order.domain.OrderItem

fun OrderItemDto.toOrderItem(): OrderItem = OrderItem(
    apparelId = apparelId.orEmpty(),
    size = size.orEmpty(),
    quantity = quantity ?: 0,
    price = price ?: 0.0,
    brandId = brandId.orEmpty()
)

fun OrderItem.toDto(): OrderItemDto = OrderItemDto(
    apparelId = apparelId,
    size = size,
    quantity = quantity,
    price = price,
    brandId = brandId
)

fun OrderDto.toOrder(): Order = Order(
    orderId = orderId.orEmpty(),
    userId = userId.orEmpty(),
    items = items?.map { it.toOrderItem() } ?: emptyList(),
    total = total ?: 0.0,
    createdAt = createdAt
)

fun Order.toDto(): OrderDto = OrderDto(
    orderId = orderId.ifBlank { null },
    userId = userId,
    items = items.map { it.toDto() },
    total = total,
    createdAt = null
)