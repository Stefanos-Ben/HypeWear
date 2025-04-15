package com.stephben.hypewear.apparel.domain



data class Apparel(
    // Firestore doc ID
    val apparelID: String = "",


    val brand: BrandInfo = BrandInfo(),

    // Main info
    val description: String = "",
    val imageUrl: String = "",

    // Price-related
    val price: Double = 0.0,
    val currency: String = "€",
    val discount: Double = 0.0,

    // Additional info
    val fabric: String = "",
    val color: String = "",


    //Eco fields
    val ecoMetrics: EcoMetrics = EcoMetrics(),
    val ecoScore: Int = 0,
    val ecoBadges: List<String> = emptyList(),

    //Categorization
    val sex: String = "",
    val category: String = "",
    val tags: List<String> = emptyList(),

    // Size & stock
    val stockPerSize: Map<String, Int> = emptyMap(),

    val createdAt: String = "",
    val updatedAt: String = "",
)