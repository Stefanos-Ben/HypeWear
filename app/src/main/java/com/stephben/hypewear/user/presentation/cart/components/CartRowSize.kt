package com.stephben.hypewear.user.presentation.cart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CartRowSize(
    sizes: List<String>,
    currentSize: String,
    onSizeChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }


    Box {
        Text(
            text = currentSize.ifBlank { "Size" },
            modifier = Modifier
                .clickable { expanded = true }
                .padding(8.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sizes.forEach { size ->
                DropdownMenuItem(
                    text = { Text(size) },
                    onClick = {
                        expanded = false
                        onSizeChange(size)
                    }
                )
            }
        }
    }
}