package com.stephben.hypewear.auth.presentation.brand_signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun BrandSignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: BrandSignUpViewModel = koinViewModel<BrandSignUpViewModel>(),
    onBackToSignIn: () -> Unit,
    onSignUpSuccess: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
    )

    LaunchedEffect(state.isSignUpComplete) {
        if (state.isSignUpComplete) {
            onSignUpSuccess()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(64.dp))

            Image(
                painter = painterResource(R.drawable.hypewear_logo_nobg_zoom),
                contentDescription = "HypeWear logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .padding(24.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Become Our Partner",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Let's grow together! Register your brand and create your own HypeWear e-shop!",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.displayName,
                onValueChange = { viewModel.onAction(BrandSignUpAction.OnDisplayNameChange(it)) },
                label = { Text(text = "Display Name")},
                supportingText = {
                    Text(
                        text = state.displayNameError,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingIcon = {
                    Icon(painter = painterResource(R.drawable.person_24), contentDescription = null)
                },
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.email,
                onValueChange = { viewModel.onAction(BrandSignUpAction.OnEmailChange(it)) },
                label = { Text("Email") },
                supportingText = {
                    Text(
                        text = state.emailError,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingIcon = {
                    Icon(painter = painterResource(R.drawable.email_24), contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                ),
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.password,
                onValueChange = { viewModel.onAction(BrandSignUpAction.OnPasswordChange(it)) },
                label = { Text("Password") },
                supportingText = {
                    Text(
                        text = state.passwordError,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                visualTransformation = if (state.passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(painter = painterResource(R.drawable.lock_24), contentDescription = null)
                },
                trailingIcon = {
                    Icon(
                        painter = if (state.passwordVisible) painterResource(R.drawable.visibility_off_24)
                        else painterResource(R.drawable.visibility_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                viewModel.onAction(BrandSignUpAction.OnPasswordVisibilityToggle)
                            }
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                ),
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.confirmPassword,
                onValueChange = { viewModel.onAction(BrandSignUpAction.OnConfirmPasswordChange(it)) },
                label = { Text("Confirm Password") },
                supportingText = {
                    Text(
                        text = state.confirmPasswordError,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                visualTransformation = if (state.confirmPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(painter = painterResource(R.drawable.lock_24), contentDescription = null)
                },
                trailingIcon = {
                    Icon(
                        painter = if (state.confirmPasswordVisible) painterResource(R.drawable.visibility_off_24)
                        else painterResource(R.drawable.visibility_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                viewModel.onAction(BrandSignUpAction.OnConfirmPasswordVisibilityToggle)
                            }
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                ),
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onAction(BrandSignUpAction.OnSignUpClick) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Not a brand owner?", color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = onBackToSignIn) {
                    Text("Back to user sign in")
                }
            }
        }
    }
}