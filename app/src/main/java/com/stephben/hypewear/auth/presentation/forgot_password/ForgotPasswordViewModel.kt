package com.stephben.hypewear.auth.presentation.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ForgotPasswordViewModel(
    //private val userRepository: UserRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    private val _state = MutableStateFlow(_root_ide_package_.com.stephben.hypewear.auth.presentation.forgot_password.ForgotPasswordState())
    val state: StateFlow<com.stephben.hypewear.auth.presentation.forgot_password.ForgotPasswordState> = _state

    fun onAction(action: com.stephben.hypewear.auth.presentation.forgot_password.ForgotPasswordAction) {
        when(action) {
            is com.stephben.hypewear.auth.presentation.forgot_password.ForgotPasswordAction.OnEmailChange ->
                _state.update { it.copy(email = action.email, message = null) }

            is _root_ide_package_.com.stephben.hypewear.auth.presentation.forgot_password.ForgotPasswordAction.OnSubmit -> resetPassword()
        }
    }

    private fun resetPassword() {
        val email = _state.value.email.trim()
        if(email.isBlank()) {
            _state.update { it.copy(message = "Please provide a valid email") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }
            try {
                auth.sendPasswordResetEmail(email).await()
                _state.update { it.copy(isLoading = false, message = "A password reset link was sent!") }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, message = e.message) }
            }
        }
    }
}