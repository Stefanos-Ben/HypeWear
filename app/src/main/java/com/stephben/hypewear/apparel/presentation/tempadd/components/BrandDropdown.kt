package com.stephben.hypewear.apparel.presentation.tempadd.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.stephben.hypewear.brand.domain.Brand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandDropdown(
    brands: List<Brand>,
    selectedBrand: Brand?,
    onBrandSelected: (Brand) -> Unit
) {
    // e.g. a simple approach
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        // TextField to show the current brand or a placeholder
        TextField(
            readOnly = true,
            value = selectedBrand?.name ?: "Select brand",
            onValueChange = { },
            label = { Text("Brand") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            brands.forEach { brand ->
                DropdownMenuItem(
                    text = { Text(brand.name) },
                    onClick = {
                        onBrandSelected(brand)
                        expanded = false
                    }
                )
            }
        }
    }
}
