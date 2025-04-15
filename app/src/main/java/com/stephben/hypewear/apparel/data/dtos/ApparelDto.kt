package com.stephben.hypewear.apparel.data.dtos

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class ApparelDto(
    @DocumentId
    val docId: String? = null,

    val brand: BrandInfoDto? = null,

    // Main info
    val description: String? = null,
    val imageUrl: String? = null,

    // Price-related
    val price: Double? = null,
    val currency: String? = null,
    val discount: Double = 0.0,

    //Additional Info
    val fabric: String? = null,
    val color: String? = null,

    // Eco fields
    val ecoMetrics: EcoMetricsDto? = null,
    val ecoScore: Int? = null,
    val ecoBadges: List<String>? = null,

    // categorization
    val sex: String? = null,
    val category: String? = null,
    val tags: List<String>? = null,

    // size & stock
    val stockPerSize: Map<String, Int>? = null,

    val trigrams: List<String>? = null ,

    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
)