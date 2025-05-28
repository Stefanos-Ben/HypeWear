package com.stephben.hypewear.user.presentation.profile

import android.net.Uri

interface ProfileAction {
    data object OnLogout: ProfileAction

    data class OnToggleDarkMode(val enabled: Boolean): ProfileAction

    data class OnUploadImage(val uri: Uri): ProfileAction
}