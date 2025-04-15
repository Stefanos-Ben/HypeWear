package com.stephben.hypewear.brand.data.dtos

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class BrandDto(
    @DocumentId
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val logoUrl: String? = null,
    val contactEmail: String? = null,

    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
)
