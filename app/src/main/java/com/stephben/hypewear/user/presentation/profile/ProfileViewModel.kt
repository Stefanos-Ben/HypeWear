package com.stephben.hypewear.user.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.auth.domain.AuthRepository
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

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val darkModePreferences: DarkModePreferences,
    private val imageUploader: ImageUploader
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            darkModePreferences.darkModeEnabled.collect { enabled ->
                _state.update { it.copy(isDarkMode = enabled) }
            }
        }

        viewModelScope.launch {
            fetchUser()
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnLogout -> { signOut() }
            is ProfileAction.OnToggleDarkMode -> { switchDarkMode(action.enabled) }
            is ProfileAction.OnUploadImage -> { uploadImage(action.uri) }
        }
    }

    private suspend fun fetchUser() =
        when (val result = userRepository.getUserById(authRepository.currentUserId() ?: "")) {
            is Result.Success -> {
                Log.i("PROFILE", "USER FETCHED SUCCESSFULLY")
                _state.update { it.copy(user = result.data) }
            }

            is Result.Failure -> {
                Log.e("PROFILE", "USER FETCH ERROR")
                _state.update { it.copy(user = User(displayName = "N/A")) }
            }
        }

    private fun switchDarkMode(enabled: Boolean) = viewModelScope.launch {
        darkModePreferences.setDarkMode(enabled)
    }

    private fun uploadImage(uri: Uri) = viewModelScope.launch {
        _state.update { it.copy(imageUploadInFlight = true) }

        try {
            val publicId = "users/${_state.value.user.userId}/profile_${System.currentTimeMillis()}"
            val url = imageUploader.upload(uri, publicId)

            when (val result = userRepository.updateUser(_state.value.user.copy(photoUrl = url))) {
                is Result.Success -> {
                    _state.update { it.copy(user = it.user.copy(photoUrl = url)) }
                }
                is Result.Failure -> {
                    Log.e("PROFILE", "IMAGE UPLOAD ERROR: ${result.exception.message.toString()}")
                }
            }
        } finally {
            _state.update { it.copy(imageUploadInFlight = false) }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

}