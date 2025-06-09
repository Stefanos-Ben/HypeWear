package com.stephben.hypewear.apparel.domain

data class ApparelFilters(
    val brand: String? = null,
    val category: String? = null,
    val sex: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val minEcoScore: Int? = null,
    val maxEcoScore: Int? = null
)
