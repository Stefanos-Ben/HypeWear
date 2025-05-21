package com.stephben.hypewear.apparel.presentation.apparel_form.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorText(msg: String?) {
    if (!msg.isNullOrBlank())
        Text(
            text = msg,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
}