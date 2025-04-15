package com.stephben.hypewear.auth.presentation.forgot_password

sealed interface ForgotPasswordAction {
    data class onEmailChange(val email: String): ForgotPasswordAction

    data object onSubmit: ForgotPasswordAction
}