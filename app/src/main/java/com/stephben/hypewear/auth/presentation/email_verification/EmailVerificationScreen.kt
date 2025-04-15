package com.stephben.hypewear.auth.presentation.email_verification

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmailVerificationScreen(
    viewModel: EmailVerificationViewModel = koinViewModel(),
    onContinue: () -> Unit,
    onBackToSignIn: () -> Unit,
) {
   val state = viewModel.state.collectAsState().value


   LaunchedEffect(Unit) {
       while (true) {
           viewModel.onAction(EmailVerificationAction.Refresh)
           delay(3_000)
       }

   }

    // If user is already verified skip.
   LaunchedEffect(state.isEmailVerified) {
       Log.i("Verification screen", "isVerified changed!")
       if (state.isEmailVerified) onContinue()

   }

   Column(
       modifier = Modifier.padding(16.dp)
   ) {
       Text(text = "Email Verification", style = MaterialTheme.typography.titleLarge)

       if (state.message != null) {
           Text(
               text = state.message,
               color = MaterialTheme.colorScheme.error,
               modifier = Modifier.padding(vertical = 8.dp)
           )
           Spacer(Modifier.height(8.dp))
       }

       if (!state.isEmailVerified) {
           Text("Your email is not yet verified. Please check your inbox or resend link!")
       }

       Spacer(modifier = Modifier.height(16.dp))

       Button(
           onClick = { viewModel.onAction(EmailVerificationAction.ResendVerificationLink) }
       ) {
           Text("Resend Verification Link")
       }

       Spacer(modifier = Modifier.height(16.dp))

       Button(
           onClick = {
               viewModel.onAction(EmailVerificationAction.LogOut)
               onBackToSignIn()
           }


       ) {
           Text("Back to Sign In")
       }

       if (state.isLoading) {
           CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
       }
   }
}