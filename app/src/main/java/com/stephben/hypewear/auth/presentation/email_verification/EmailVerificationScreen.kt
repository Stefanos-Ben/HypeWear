package com.stephben.hypewear.auth.presentation.email_verification

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
           viewModel.onAction(EmailVerificationAction.CheckVerificationStatus)
           delay(1_000)
           if (!state.isEmailVerified) viewModel.onAction(EmailVerificationAction.Refresh)
           delay(1_500)
       }
   }

    // If user is already verified skip.
   LaunchedEffect(state.isEmailVerified) {
       Log.i("Verification screen", "isVerified changed to ${state.isEmailVerified}!")
       if (state.isEmailVerified) onContinue()

   }

    LaunchedEffect(state.isLogoutComplete) {
        if (state.isLogoutComplete) {
            onBackToSignIn()
        }
    }

   Column(
       modifier =
           Modifier
               .fillMaxSize()
               .background(MaterialTheme.colorScheme.background)
               .padding(horizontal = 16.dp),
       horizontalAlignment = Alignment.CenterHorizontally,
   ) {
       Spacer(modifier = Modifier.height(64.dp))

       Text(
           text = "Email Verification",
           style = MaterialTheme.typography.titleLarge,
           color = MaterialTheme.colorScheme.onBackground,
           fontSize = 32.sp,
           fontWeight = FontWeight.SemiBold
       )

       if (state.message != null) {
           Text(
               text = state.message,
               style = MaterialTheme.typography.bodyLarge,
               color = MaterialTheme.colorScheme.error,
               modifier = Modifier.padding(vertical = 8.dp)
           )
           Spacer(Modifier.height(8.dp))
       }

       if (!state.isEmailVerified) {
           Text(
               text = "Your email is not yet verified. Please check your inbox or resend link!",
               style = MaterialTheme.typography.bodyLarge,
               color = MaterialTheme.colorScheme.onBackground,
               textAlign = TextAlign.Center,
           )
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
           }


       ) {
           Text("Back to Sign In")
       }

       if (state.isLoading) {
           CircularProgressIndicator(modifier = Modifier
               .fillMaxWidth()
               .padding(top = 16.dp))
       }
   }
}