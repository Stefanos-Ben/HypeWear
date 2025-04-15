package com.stephben.hypewear.auth.presentation.signin

import android.util.Log
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
import com.stephben.hypewear.R
import com.stephben.hypewear.core.presentation.ui.theme.components.InformationBox
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel(),
    onSignInSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onEmailVerificationNeeded: () -> Unit,
    onNavigateToBrandSignUp: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            Log.i("LOGIN", "LOGGED IN")
            if (state.isEmailVerified){
                Log.i("LOGIN", "EMAIL VERIFIED")
                onSignInSuccess()
            } else {
                Log.i("LOGIN", "EMAIL NOT VERIFIED")
                onEmailVerificationNeeded()
            }
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


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text= "Sign In",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.email,
            onValueChange = { viewModel.onAction(SignInAction.onEmailChange(it)) },
            label = { Text("Email") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.password,
            onValueChange = { viewModel.onAction(SignInAction.onPasswordChange(it)) },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onAction(SignInAction.OnSignInClick) },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Forgot Password?", color = MaterialTheme.colorScheme.onTertiaryContainer)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an account?", color = MaterialTheme.colorScheme.onTertiaryContainer)
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = onNavigateToSignUp) {
                Text("Sign Up")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        InformationBox(
            message = "Do you own a Clothing Brand?",
            link = "Register here!",
            onTextClick = onNavigateToBrandSignUp,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        )

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}