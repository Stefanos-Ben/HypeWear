package com.stephben.hypewear.auth.presentation.signup

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel(),
    onSignUpSuccess: () -> Unit,
    onBackToSignIn: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state.isSignUpComplete) {
        if (state.isSignUpComplete) {
            onSignUpSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.hypewear_logo_nobg_zoom),
            contentDescription = "HypeWear logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .padding(24.dp)
        )

        Text(
            text = "Join The Hype !",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.displayName,
            onValueChange = { viewModel.onAction(SignUpAction.OnDisplayNameChange(it)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            label = { Text("Display Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.email,
            onValueChange = { viewModel.onAction(SignUpAction.OnEmailChange(it)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.password,
            onValueChange = { viewModel.onAction(SignUpAction.OnPasswordChange(it)) },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onAction(SignUpAction.OnSignUpClick) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account?", color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = onBackToSignIn) {
                Text("Sign In")
            }
        }
    }
}