package com.stephben.hypewear.brand.presentation.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.R

@Composable
fun CollectionScreen(
    bottomBar: @Composable () -> Unit,
    onAddApparel: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddApparel,
                shape = CircleShape,
                content = {
                    Icon(painter = painterResource(R.drawable.add_24), contentDescription = null)
                },
                modifier = Modifier.padding(bottom = 32.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 4.dp)
                .padding(contentPadding)
        ) {
            Text(
                text = "COLLECTION",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 32.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            Box(modifier =  Modifier.align(Alignment.BottomStart)){
                bottomBar()
            }
        }
    }

}