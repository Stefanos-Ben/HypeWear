package com.stephben.hypewear.auth.presentation.brand_signup

import com.stephben.hypewear.auth.presentation.signup.SignUpAction

interface BrandSignUpAction {
    data class OnDisplayNameChange(val displayName: String): BrandSignUpAction

    data class OnEmailChange(val email: String): BrandSignUpAction

    data class OnPasswordChange(val password: String): BrandSignUpAction

    data object OnSignUpClick: BrandSignUpAction
}