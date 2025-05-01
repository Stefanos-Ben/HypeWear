package com.stephben.hypewear.auth.presentation.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()


    init {
        onAction(SignInAction.OnCheckAuthState)
    }

    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.OnEmailChange ->
                _state.update { it.copy(email = action.email, errorMessage = null) }

            is SignInAction.OnPasswordChange ->
                _state.update { it.copy(password = action.password, errorMessage = null) }

            SignInAction.OnSignInReset -> {
                val isUserLoggedIn = auth.currentUser != null
                val verified = auth.currentUser?.isEmailVerified ?: false
                _state.update {
                    it.copy(
                        isLoggedIn = isUserLoggedIn,
                        isEmailVerified = verified,
                        email = "",
                        password = "",
                        errorMessage = null,
                    )
                }
                Log.i(
                    "Sign in reset",
                    "isLoggedIn is ${state.value.isLoggedIn}, isEmailVerified is ${state.value.isEmailVerified}"
                )
            }

            SignInAction.OnSignInClick -> signIn()
            SignInAction.OnPasswordVisibilityToggle ->
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }

            SignInAction.OnCheckAuthState ->
                viewModelScope.launch {
                val isLoggedIn = authRepository.isUserLoggedIn()
                _state.update {
                    Log.i("LOGIN", "The user is $isLoggedIn")
                    it.copy(isLoggedIn = isLoggedIn)
                }
            }
        }
    }

    private fun signIn() = viewModelScope.launch {
        val email = _state.value.email.trim()
        val pass = _state.value.password

        if (email.isBlank() || pass.isBlank()) {
            _state.update { it.copy(errorMessage = "Please fill both your email and password!") }
            return@launch
        }

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        when (val result = authRepository.signInWithEmail(email, pass)) {
            is Result.Success -> {

                val user = result.data
                val isVerified = auth.currentUser?.isEmailVerified ?: false
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            isEmailVerified = isVerified,
                            userType = user.userType
                        )
                    }
            }

            is Result.Failure -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = result.exception.message,
                    )
                }
                Log.e("SIGN_IN", "Sign in failed: ${result.exception.message}")
            }
        }

    }

}