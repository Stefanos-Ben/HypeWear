package com.stephben.hypewear.auth.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    fun onAction(action: SignInAction) {
        when(action) {
           is SignInAction.onEmailChange ->
                _state.update { it.copy(email = action.email, errorMessage = null) }
           is SignInAction.onPasswordChange ->
               _state.update { it.copy(password = action.password, errorMessage = null) }

           SignInAction.OnSignInClick -> signIn()
        }
    }

    private fun signIn() {
        val email = _state.value.email
        val pass = _state.value.password
        if (email.isBlank() || pass.isBlank()) {
            _state.update { it.copy(errorMessage = "Fields can't be empty") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when(val result = authRepository.signInWithEmail(email, pass)) {
                is Result.Success -> {
                    val firebaseUser = FirebaseAuth.getInstance().currentUser
                    val verified = firebaseUser?.isEmailVerified ?: false
                    _state.update { it.copy(isLoading = false, isLoggedIn = true, isEmailVerified = verified) }
                }
                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.exception.message) }
                }
            }
        }
    }
}