package com.stephben.hypewear.user.presentation.profile

import com.stephben.hypewear.user.domain.User

data class ProfileState(
    val user: User = User(),

    val imageUploadInFlight: Boolean = false,
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = false,
)
