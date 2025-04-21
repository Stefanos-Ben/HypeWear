package com.stephben.hypewear.auth.domain

import com.stephben.hypewear.core.domain.utils.Result

interface AuthRepository {
    suspend fun signUpWithEmail(email: String, password: String, displayName: String,): Result<Unit>
    suspend fun signUpBrandWithEmail(
        email: String,
        password: String,
        displayName: String
    ): Result<Unit>
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signOut()
    fun currentUserId(): String?
    fun isUserLoggedIn(): Boolean

    suspend fun signInWithGoogle(idToken: String): Result<Unit>
}