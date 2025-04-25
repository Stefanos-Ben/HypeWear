package com.stephben.hypewear.auth.presentation.forgot_password

sealed interface ForgotPasswordAction {
    data class OnEmailChange(val email: String): ForgotPasswordAction

    data object OnSubmit: ForgotPasswordAction
}