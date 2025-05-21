package com.stephben.hypewear.core.presentation.splash_screen

data class SplashScreenState(
    val isLoggedIn : Boolean = false,
    val isEmailVerified: Boolean = false,
    val isLoading: Boolean = true,
    val userType: String? = null,
)