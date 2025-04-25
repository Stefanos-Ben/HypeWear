package com.stephben.hypewear.auth.presentation.brand_signup


interface BrandSignUpAction {
    data class OnDisplayNameChange(val displayName: String): BrandSignUpAction

    data class OnEmailChange(val email: String): BrandSignUpAction

    data class OnPasswordChange(val password: String): BrandSignUpAction

    data object OnPasswordVisibilityToggle: BrandSignUpAction

    data class OnConfirmPasswordChange(val confirmPassword: String): BrandSignUpAction

    data object OnConfirmPasswordVisibilityToggle: BrandSignUpAction

    data object OnSignUpClick: BrandSignUpAction
}