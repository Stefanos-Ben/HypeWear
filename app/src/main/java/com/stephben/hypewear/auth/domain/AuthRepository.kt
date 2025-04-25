package com.stephben.hypewear.auth.domain

import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.User

interface AuthRepository {
    suspend fun signUpWithEmail(email: String, password: String, displayName: String): Result<Unit>
    suspend fun signUpBrandWithEmail(
        email: String,
        password: String,
        displayName: String
    ): Result<Unit>
    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun signOut()
    fun currentUserId(): String?
    fun isUserLoggedIn(): Boolean

    suspend fun signInWithGoogle(idToken: String): Result<Unit>

    suspend fun updateVerificationStatus(id: String): Result<Unit>
}