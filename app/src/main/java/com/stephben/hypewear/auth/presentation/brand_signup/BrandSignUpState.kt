package com.stephben.hypewear.auth.presentation.brand_signup

data class BrandSignUpState(
    val displayName: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignUpComplete: Boolean = false
)