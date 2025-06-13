package com.stephben.hypewear.brand.presentation.brand_home

import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.brand.domain.Brand

data class BrandHomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val brand: Brand? = null,
    val totalRevenue: Double = 0.0,
    val totalOrders: Int = 0,
    val totalItemsSold: Int = 0,
    val topSellingApparelId: String = "",
    val topSellingApparel: Apparel? = null
)
