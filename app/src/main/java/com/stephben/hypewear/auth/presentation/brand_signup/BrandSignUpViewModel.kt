package com.stephben.hypewear.auth.presentation.brand_signup

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
            is BrandSignUpAction.OnSignUpClick -> signUp()
        }
    }

    private fun signUp() {
        val currentState = _state.value

        if (currentState.email.isBlank() ||
            currentState.password.isBlank() ||
            currentState.displayName.isBlank()
        ) {
            _state.update { it.copy(errorMessage = "All fields are required!") }
            return
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