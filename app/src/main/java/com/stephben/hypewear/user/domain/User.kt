package com.stephben.hypewear.user.domain

data class User(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String = "",

    val userType: String = "default",
    val brandId: String = "",

    val isEmailVerified: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
)
