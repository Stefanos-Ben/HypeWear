package com.stephben.hypewear.user.domain

import com.stephben.hypewear.core.domain.utils.Result

interface UserRepository {
    suspend fun getCurrentUserDoc(): Result<User>
    suspend fun getUserFavorites(): Result<List<String>>
    suspend fun addUserFavorites(apparelId: String): Result<Unit>
    suspend fun removeUserFavorites(apparelId: String): Result<Unit>
    suspend fun isFavorite(apparelId: String): Boolean
    suspend fun getUserCart(): Result<List<Cart>>
    suspend fun updateUserCart(cart: List<Cart>): Result<Unit>
    suspend fun getUserById(userId: String): Result<User>
    suspend fun updateUser(user: User): Result<Unit>
}