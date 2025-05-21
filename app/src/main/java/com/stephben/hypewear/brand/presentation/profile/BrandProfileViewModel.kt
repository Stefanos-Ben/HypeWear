package com.stephben.hypewear.brand.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.UserRepository
import com.stephben.hypewear.user.presentation.profile.ProfileAction
import com.stephben.hypewear.user.presentation.profile.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrandProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(BrandProfileState())
    val state: StateFlow<BrandProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            when(val result = userRepository.getUserById(authRepository.currentUserId() ?: "")){
                is Result.Success -> {
                    Log.i("BRAND PROFILE", "USER FETCHED SUCCESSFULLY")
                    _state.update { it.copy(displayName = result.data.displayName) }
                }

                is Result.Failure -> {
                    Log.e("BRAND PROFILE", "USER FETCH ERROR")
                    _state.update { it.copy(displayName = "N/A") }
                }
            }

        }
    }

    fun onAction(action: BrandProfileAction) {
        when(action){
            is BrandProfileAction.OnLogout -> {
                signOut()
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}