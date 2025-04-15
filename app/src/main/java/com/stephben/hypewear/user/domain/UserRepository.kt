package com.stephben.hypewear.user.domain

import com.stephben.hypewear.core.domain.utils.Result

interface UserRepository {
    suspend fun signUpWithEmail(email: String, password: String, displayName: String,): Result<Unit>
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signOut()

    fun isUserLoggedIn(): Boolean


    suspend fun getCurrentUserDoc(): Result<User>
    suspend fun getUserById(userId: String): Result<User>

    suspend fun signInWithGoogle(idToken: String): Result<Unit>
}