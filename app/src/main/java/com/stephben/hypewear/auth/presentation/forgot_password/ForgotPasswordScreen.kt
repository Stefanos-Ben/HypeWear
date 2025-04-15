package com.stephben.hypewear.auth.presentation.forgot_password

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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreen(
    viewModel: com.stephben.hypewear.auth.presentation.forgot_password.ForgotPasswordViewModel = koinViewModel(),
    onPasswordResetSent: () -> Unit,
    onBackToSignIn: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val resetMessage = stringResource(R.string.pass_reset_message)

    LaunchedEffect(state.message) {
        if (state.message == resetMessage) {
            onPasswordResetSent()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Forgot Password", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.email,
            onValueChange = { viewModel.onAction(ForgotPasswordAction.onEmailChange(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        if (state.message != null) {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error,
                modifier =  Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.onAction(ForgotPasswordAction.onSubmit) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Reset Password")
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = onBackToSignIn) {
            Text("Back to Sign In")
        }
    }
}