package com.stephben.hypewear.core.presentation.splash_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository,
): ViewModel() {
    private val tag = "SPLASH VM"
    private val _state: MutableStateFlow<SplashScreenState> = MutableStateFlow(SplashScreenState())
    val state: StateFlow<SplashScreenState> = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                isLoggedIn = false,
                isEmailVerified = false,
                userType = null,
            )
        }
        checkUserAuth()
    }

    private fun checkUserAuth() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser == null){
                return@launch
            } else {
                _state.update {
                    it.copy(
                        isLoggedIn = true,
                        isEmailVerified = currentUser.isEmailVerified,
                    )
                }
                getUserType(currentUser.uid)
            }
        }
    }

    private fun getUserType(id: String) {
        viewModelScope.launch {
            return@launch try {
                when (val result = userRepository.getUserById(id)){
                    is Result.Failure -> {
                        throw IllegalStateException("Couldn't determine user's identity")
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                userType = result.data.userType,
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, "ERROR: ${e.message}")
                _state.update {
                    it.copy(
                        userType = null,
                        isLoading = false
                    )
                }
            }
        }
    }

}