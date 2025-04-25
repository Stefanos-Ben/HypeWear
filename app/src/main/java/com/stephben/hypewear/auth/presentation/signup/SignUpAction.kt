package com.stephben.hypewear.auth.presentation.signup

sealed interface SignUpAction {
    data class OnDisplayNameChange(val displayName: String): SignUpAction

    data class OnEmailChange(val email: String): SignUpAction

    data class OnPasswordChange(val password: String): SignUpAction

    data object OnPasswordVisibilityToggle: SignUpAction

    data class OnConfirmPasswordChange(val confirmPassword: String): SignUpAction

    data object OnConfirmPasswordVisibilityToggle: SignUpAction

    data object OnSignUpClick: SignUpAction
}