package com.stephben.hypewear.auth.presentation.signin

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false,
    val isEmailVerified: Boolean = false,
)
