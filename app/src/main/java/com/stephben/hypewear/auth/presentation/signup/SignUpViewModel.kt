package com.stephben.hypewear.auth.presentation.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.core.domain.utils.Result
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
            is SignUpAction.OnConfirmPasswordChange ->
                _state.update { it.copy(confirmPassword = action.confirmPassword, errorMessage = null) }
            SignUpAction.OnSignUpClick -> signUp()
            SignUpAction.OnConfirmPasswordVisibilityToggle ->
                _state.update { it.copy(confirmPasswordVisible =  !it.confirmPasswordVisible) }
            SignUpAction.OnPasswordVisibilityToggle -> {
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }
            }
        }
    }

    private fun signUp() {
        _state.update {
            it.copy(
                errorMessage = "",
                displayNameError = "",
                emailError = "",
                passwordError = "",
                confirmPasswordError = "",
            )
        }
        val current = _state.value
        when{
           current.displayName.isBlank() -> {
               _state.update { it.copy(displayNameError = "Display Name field is required!") }
               return
           }
           current.email.isBlank() -> {
               _state.update { it.copy(emailError = "Email field is required!") }
               return
           }
           current.password.isBlank() -> {
               _state.update { it.copy(passwordError = "Password field is required!") }
               return
           }
           current.confirmPassword.isBlank() -> {
               _state.update { it.copy(confirmPasswordError = "Confirm Password field is required!") }
               return
           }
           !Patterns.EMAIL_ADDRESS.matcher(current.email).matches() -> {
               _state.update { it.copy(emailError = "Enter a valid email address!") }
               return
           }
           current.password != current.confirmPassword -> {
               _state.update { it.copy(confirmPasswordError = "Passwords must match!") }
               return
           }

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