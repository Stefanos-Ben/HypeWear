package com.stephben.hypewear.apparel.presentation.apparel_form.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.R
import com.stephben.hypewear.core.domain.utils.SizeCharts
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun StockRow(
    size: String,
    qty: String,
    allowedSizes: List<String>,
    onSizeChange: (String) -> Unit,
    onQtyChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    Log.i("SIZE", size)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExposedDropdown(
            label = "Size",
            options = allowedSizes,
            selected = if (size.startsWith("new")) allowedSizes[0] else size,
            onSelect = onSizeChange,
            error = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            value = qty,
            onValueChange = onQtyChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.Bottom),
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onRemove) {
            Icon(
                painter = painterResource(R.drawable.delete_24),
                contentDescription = "Remove size",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview
@Composable
private fun StockRowPrev() {
    HypeWearTheme {
        StockRow("M", "3", SizeCharts.menWaist, {}, {}) { }
    }
}