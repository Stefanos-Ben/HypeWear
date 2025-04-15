package com.stephben.hypewear.auth.presentation.signin

sealed interface SignInAction {
    data class onEmailChange(val email: String): SignInAction

    data class onPasswordChange(val password: String): SignInAction

    data object OnSignInClick: SignInAction
}