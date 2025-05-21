package com.stephben.hypewear.brand.presentation.brand_home

import com.stephben.hypewear.brand.domain.Brand

data class BrandHomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val brand: Brand? = null,
)
