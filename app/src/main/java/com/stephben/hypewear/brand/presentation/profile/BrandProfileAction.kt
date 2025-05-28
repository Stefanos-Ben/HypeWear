package com.stephben.hypewear.brand.presentation.profile

import android.net.Uri

interface BrandProfileAction {
    data object OnLogout: BrandProfileAction

    data class ToggleDarkMode(val enabled: Boolean): BrandProfileAction

    data class UploadLogo(val uri: Uri): BrandProfileAction
}