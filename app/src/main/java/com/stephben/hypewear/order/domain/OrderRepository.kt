package com.stephben.hypewear.order.domain

import com.stephben.hypewear.core.domain.utils.Result

interface OrderRepository {
    suspend fun createOrder(userId: String, items: List<OrderItem>): Result<Order>
    suspend fun getUserOrders(userId: String): Result<List<Order>>
    suspend fun getBrandOrders(brandId: String): Result<List<Order>>
}