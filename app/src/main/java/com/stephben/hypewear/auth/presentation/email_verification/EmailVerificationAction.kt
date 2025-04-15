package com.stephben.hypewear.auth.presentation.email_verification

sealed interface EmailVerificationAction {
    data object CheckVerificationStatus : EmailVerificationAction

    data object ResendVerificationLink: EmailVerificationAction

    data object Refresh: EmailVerificationAction

    data object LogOut: EmailVerificationAction
}