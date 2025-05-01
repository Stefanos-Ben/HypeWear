package com.stephben.hypewear.user.data.dtos

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserDto(
    @DocumentId
    val userId: String? = null,

    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,

    val userType: String? = null,
    val brandId: String? = null,

    val favorites: List<String>? = null,
    val cart: List<String>? = null,

    val isEmailVerified: Boolean? = null,

    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
)
