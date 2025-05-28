package com.stephben.hypewear.brand.presentation.profile

import com.stephben.hypewear.brand.domain.Brand
import com.stephben.hypewear.user.domain.User

data class BrandProfileState(
    val brand: Brand = Brand(),
    val user: User = User(),
    val logoUploadInFlight: Boolean = false,
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = false,
)
