package com.stephben.hypewear.auth.presentation.signup

data class SignUpState(
    val displayName: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignUpComplete: Boolean = false,
)
