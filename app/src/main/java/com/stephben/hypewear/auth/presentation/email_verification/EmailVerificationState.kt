package com.stephben.hypewear.auth.presentation.email_verification

data class EmailVerificationState(
    val isLoading: Boolean = false,
    val message: String? = "",
    val userType: String? = "default",
    val isEmailVerified: Boolean = false,
    val isLogoutComplete: Boolean = false,
)