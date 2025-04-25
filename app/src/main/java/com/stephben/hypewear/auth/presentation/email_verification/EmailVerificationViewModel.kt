package com.stephben.hypewear.auth.presentation.email_verification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.stephben.hypewear.auth.domain.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EmailVerificationViewModel(
    private val auth: FirebaseAuth,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(EmailVerificationState())
    val state: StateFlow<EmailVerificationState> = _state

    init {
        _state.update { it.copy(message = null) }
        refreshUser()
        auth.currentUser?.sendEmailVerification()
    }

    fun onAction(action: EmailVerificationAction) {
        when(action) {
            EmailVerificationAction.CheckVerificationStatus -> checkVerificationStatus()
            EmailVerificationAction.ResendVerificationLink -> resendVerification()
            EmailVerificationAction.Refresh -> refreshUser()
            EmailVerificationAction.LogOut -> logOutUser()
        }
    }

    private fun logOutUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            authRepository.signOut()
            delay(500)
            _state.update { it.copy(isLoading = false, isLogoutComplete = true) }
        }
    }

    private fun refreshUser() {
        _state.update {
            it.copy(message = null)
        }
        val user = auth.currentUser
        if (user == null) {
            _state.update { it.copy(message = "No logged in user") }
            return
        }
        viewModelScope.launch {
            try {
                user.reload().await()
                Log.e("Email Verification", "Refreshed user")
            } catch (e: Exception) {
                Log.e("Email Verification", "Error with user refreshing")
                _state.update { it.copy(message = e.message) }
            }
        }
    }

    private fun resendVerification() {
        _state.update {
            it.copy(isLoading = true, message = null)
        }
        val user = auth.currentUser
        if (user == null) {
            _state.update { it.copy( isLoading = false, message = "No logged in user") }
            return
        }
        viewModelScope.launch {
            try {
                user.sendEmailVerification().await()
                _state.update { it.copy(isLoading = false, message = "Verification link sent!") }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, message = e.message) }
            }
        }
    }

    private fun checkVerificationStatus() {
        val user = auth.currentUser
        if (user == null) {
            _state.update { it.copy(message = "No logged in user") }
            return
        }
        Log.e("Email Verification", "Checked verification status and it's " +
                "${user.isEmailVerified}")
        if(state.value.isEmailVerified != user.isEmailVerified){
            _state.update {
                it.copy(isEmailVerified = user.isEmailVerified, message = null)
            }
        }
        if(state.value.isEmailVerified){
            viewModelScope.launch {
                authRepository.updateVerificationStatus(user.uid)
            }
        }

    }
}