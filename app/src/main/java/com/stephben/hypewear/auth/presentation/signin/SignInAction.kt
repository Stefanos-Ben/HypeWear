package com.stephben.hypewear.auth.presentation.signin

sealed interface SignInAction {
    data class OnEmailChange(val email: String): SignInAction

    data class OnPasswordChange(val password: String): SignInAction

    data object OnCheckAuthState : SignInAction

    data object OnPasswordVisibilityToggle: SignInAction

    data object OnSignInReset: SignInAction

    data object OnSignInClick: SignInAction
}