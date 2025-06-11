package com.stephben.hypewear.user.presentation.cart.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun CartHeader(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Cart",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp
        )
    }
}

@Preview
@Composable
private fun CartHeaderPrev() {
    HypeWearTheme {
        CartHeader()
    }
}