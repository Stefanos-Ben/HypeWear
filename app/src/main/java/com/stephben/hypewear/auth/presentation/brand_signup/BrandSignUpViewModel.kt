package com.stephben.hypewear.auth.presentation.brand_signup

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

class BrandSignUpViewModel(
    private val authRepository: AuthRepository,
): ViewModel() {
    private val _state = MutableStateFlow(BrandSignUpState())
    val state: StateFlow<BrandSignUpState> = _state.asStateFlow()

    fun onAction(action: BrandSignUpAction) {
        when(action) {
            is BrandSignUpAction.OnDisplayNameChange ->
                _state.update { it.copy(displayName = action.displayName, errorMessage = null) }
            is BrandSignUpAction.OnEmailChange ->
                _state.update { it.copy(email = action.email, errorMessage = null) }
            is BrandSignUpAction.OnPasswordChange ->
                _state.update { it.copy(password = action.password, errorMessage = null) }
            is BrandSignUpAction.OnConfirmPasswordChange ->
                _state.update { it.copy(confirmPassword = action.confirmPassword, errorMessage = null) }
            is BrandSignUpAction.OnSignUpClick -> signUp()
            is BrandSignUpAction.OnPasswordVisibilityToggle ->
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }
            is BrandSignUpAction.OnConfirmPasswordVisibilityToggle ->
                _state.update { it.copy(confirmPasswordVisible = !it.confirmPasswordVisible) }
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
        val currentState = _state.value

        when{
            currentState.displayName.isBlank() -> {
                _state.update { it.copy(displayNameError = "Display Name field is required!") }
                return
            }
            currentState.email.isBlank() -> {
                _state.update { it.copy(emailError = "Email field is required!") }
                return
            }
            currentState.password.isBlank() -> {
                _state.update { it.copy(passwordError = "Password field is required!") }
                return
            }
            currentState.confirmPassword.isBlank() -> {
                _state.update { it.copy(confirmPasswordError = "Confirm Password field is required!") }
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches() -> {
                _state.update { it.copy(emailError = "Enter a valid email address!") }
                return
            }
            currentState.password != currentState.confirmPassword -> {
                _state.update { it.copy(confirmPasswordError = "Passwords must match!") }
                return
            }

        }

        viewModelScope.launch {
            _state.update{ it.copy(isLoading = true, errorMessage = null) }

            when(val result = authRepository.signUpBrandWithEmail(
                email = currentState.email,
                password = currentState.password,
                displayName = currentState.displayName
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