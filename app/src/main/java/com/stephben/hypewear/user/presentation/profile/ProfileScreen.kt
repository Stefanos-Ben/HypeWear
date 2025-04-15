package com.stephben.hypewear.user.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
    onLogout: () -> Unit,
    bottomBar: @Composable () -> Unit,
) {

    val state = viewModel.state.collectAsState().value

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Logged in as ${state.displayName}",
                color = MaterialTheme.colorScheme.onBackground
            )
            Button(
                onClick = {
                    viewModel.onAction(ProfileAction.OnLogout)
                    onLogout()
                }
            ) {
                Text("Logout")
            }
        }

        Box(modifier =  Modifier.align(Alignment.BottomStart)){
            bottomBar()
        }

    }

}