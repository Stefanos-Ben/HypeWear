package com.stephben.hypewear.order.data

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.core.domain.utils.COLLECTION_APPARELS
import com.stephben.hypewear.core.domain.utils.COLLECTION_ORDERS
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.order.data.mappers.toDto
import com.stephben.hypewear.order.data.mappers.toOrder
import com.stephben.hypewear.order.domain.Order
import com.stephben.hypewear.order.domain.OrderItem
import com.stephben.hypewear.order.domain.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class OrderRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher
): OrderRepository {
    override suspend fun createOrder(userId: String, items: List<OrderItem>): Result<Order> {
        return try {
            withContext(ioDispatcher){
                val orderRef = firestore.collection(COLLECTION_ORDERS).document()
                val batch = firestore.batch()
                val total = items.sumOf { it.price * it.quantity }
                val order = Order(
                    orderId = orderRef.id,
                    userId = userId,
                    items = items,
                    total = total,
                    createdAt = null
                )
                val brandIds = items.map { it.brandId }.distinct()
                val dto = order.toDto().copy(brandIds = brandIds)
                batch.set(orderRef, dto)
                items.forEach { item ->
                    val apparelRef = firestore.collection(COLLECTION_APPARELS).document(item.apparelId)
                    batch.update(
                        apparelRef,
                        "stockPerSize.${item.size}",
                        FieldValue.increment(-item.quantity.toLong())
                    )
                }
                batch.commit().await()
                Result.Success(order)
            }
        } catch (e: Exception) {
            Log.e("OrderRepo", "Checkout failed: ", e)
            Result.Failure(e)
        }
    }

    override suspend fun getUserOrders(userId: String): Result<List<Order>> {
        return try {
            withContext(ioDispatcher) {
                val docs = firestore.collection(COLLECTION_ORDERS)
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                val orders = docs.documents.mapNotNull { it.toObject(OrderDto::class.java)?.toOrder() }
                Result.Success(orders)
            }
        } catch (e: Exception){
            Log.e("OrderRepo", "User order fetching failed: ", e)
            Result.Failure(e)
        }
    }

    override suspend fun getBrandOrders(brandId: String): Result<List<Order>> {
        return try {
            withContext(ioDispatcher){
                val snapshot = firestore
                    .collection(COLLECTION_ORDERS)
                    .whereArrayContains("brandIds", brandId)
                    .get()
                    .await()

                val orders = snapshot.documents
                    .mapNotNull { it.toObject(OrderDto::class.java)?.toOrder() }

                return@withContext Result.Success(orders)
            }

        } catch (e: Exception) {
            Log.e("ORDER REPO", "Error getting brand orders: ", e)
            Result.Failure(e)
        }
    }
}