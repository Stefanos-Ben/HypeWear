package com.stephben.hypewear.auth.presentation.forgot_password

data class ForgotPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
)
