package com.stephben.hypewear.brand.presentation.brand_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BrandHomeScreen(modifier: Modifier = Modifier) {
     Column(
         modifier = modifier
             .fillMaxSize()
             .background(MaterialTheme.colorScheme.background),
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center
     ) {
         Text(
             text = "BRAND HOME",
             style = MaterialTheme.typography.titleLarge,
             fontSize = 32.sp,
             fontWeight = FontWeight.SemiBold,
             color = MaterialTheme.colorScheme.onBackground
         )
     }
}