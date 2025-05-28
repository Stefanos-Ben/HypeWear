package com.stephben.hypewear.brand.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.brand.domain.Brand
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.core.data.DarkModePreferences
import com.stephben.hypewear.core.domain.utils.ImageUploader
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.User
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrandProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val brandRepository: BrandRepository,
    private val darkModePreferences: DarkModePreferences,
    private val imageUploader: ImageUploader
) : ViewModel() {
    private val _state = MutableStateFlow(BrandProfileState())
    val state: StateFlow<BrandProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            darkModePreferences.darkModeEnabled.collect { enabled ->
                _state.update { it.copy(isDarkMode = enabled) }
            }
        }

        viewModelScope.launch {
            fetchUser()
            if (_state.value.user.userId.isNotBlank()) fetchBrand()
        }
    }


    fun onAction(action: BrandProfileAction) {
        when (action) {
            is BrandProfileAction.OnLogout -> { signOut() }
            is BrandProfileAction.ToggleDarkMode -> { switchDarkMode(action.enabled) }
            is BrandProfileAction.UploadLogo -> { uploadLogo(action.uri) }
        }
    }

    private fun switchDarkMode(enabled: Boolean) = viewModelScope.launch {
        darkModePreferences.setDarkMode(enabled)
    }


    private suspend fun fetchUser() {
        when (val result = userRepository.getUserById(authRepository.currentUserId() ?: "")) {
            is Result.Success -> {
                Log.i("BRAND PROFILE", "USER FETCHED SUCCESSFULLY")
                _state.update { it.copy(user = result.data) }
            }

            is Result.Failure -> {
                Log.e("BRAND PROFILE", "USER FETCH ERROR")
                _state.update { it.copy(user = User(displayName = "N/A")) }
            }
        }
    }

    private suspend fun fetchBrand() {
        when (val result = brandRepository.getCurrentBrand()) {
            is Result.Success -> {
                Log.i("BRAND PROFILE", "BRAND FETCHED SUCCESSFULLY")
                _state.update { it.copy(brand = result.data) }
            }

            is Result.Failure -> {
                Log.e("BRAND PROFILE", "USER FETCH ERROR")
                _state.update { it.copy(brand = Brand(name = "N/A")) }
            }
        }
    }

    private fun uploadLogo(uri: Uri) = viewModelScope.launch {
        _state.update { it.copy(logoUploadInFlight = true) }

        try {
            val publicId = "brands/${_state.value.user.brandId}/logo_${System.currentTimeMillis()}"
            val url = imageUploader.upload(uri, publicId)

            when(val result = brandRepository.updateBrand(_state.value.brand.copy(logoUrl = url))) {
                is Result.Success -> {
                    _state.update { it.copy(brand = it.brand.copy(logoUrl = url)) }
                }
                is Result.Failure -> {
                    Log.e("BRAND PROFILE", "IMAGE UPLOAD ERROR: ${result.exception.message.toString()}")
                }
            }
        } finally {
            _state.update { it.copy(logoUploadInFlight = false) }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}