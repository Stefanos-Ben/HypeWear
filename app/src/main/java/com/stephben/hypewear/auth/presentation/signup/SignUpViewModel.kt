package com.stephben.hypewear.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository,
): ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun onAction(action: SignUpAction) {
        when(action) {
            is SignUpAction.OnDisplayNameChange ->
                _state.update { it.copy(displayName = action.displayName, errorMessage = null) }
            is SignUpAction.OnEmailChange ->
                _state.update { it.copy(email = action.email, errorMessage = null) }
            is SignUpAction.OnPasswordChange ->
                _state.update { it.copy(password = action.password, errorMessage = null) }
            SignUpAction.OnSignUpClick -> signUp()
        }
    }

    private fun signUp() {
        val current = _state.value
        if (current.email.isBlank() || current.password.isBlank() || current.displayName.isBlank()){
            _state.update { it.copy(errorMessage = "All fields are required") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when(val result = authRepository.signUpWithEmail(
                email = current.email,
                password = current.password,
                displayName = current.displayName
            )) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, isSignUpComplete = true) }
                }
                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.exception.message) }
                }
            }
        }
    }
}