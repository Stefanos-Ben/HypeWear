package com.stephben.hypewear.auth.presentation.brand_signup

data class BrandSignUpState(
    val displayName: String = "",
    val displayNameError: String = "",
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val passwordVisible: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordError: String = "",
    val confirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignUpComplete: Boolean = false
)