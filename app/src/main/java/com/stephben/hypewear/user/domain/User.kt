package com.stephben.hypewear.user.domain

data class User(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String = "",

    val userType: String = "default",
    val brandId: String = "",


    val favorites: List<String> = emptyList(),
    val cart: List<Cart> = emptyList(),

    val isEmailVerified: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String = "",
)
