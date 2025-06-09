package com.stephben.hypewear.apparel.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.core.domain.utils.ApparelChoices

@Composable
fun FilterDialog(
    currentFilters: FilterOptions,
    onApply:  (FilterOptions) -> Unit,
    onDismiss: () -> Unit,
) {



    var brand by remember { mutableStateOf(currentFilters.brand) }
    var category by remember { mutableStateOf(currentFilters.category) }
    var sex by remember { mutableStateOf(currentFilters.sex) }


    val defaultMinPrice = 0f
    val defaultMaxPrice = 1000f
    var priceRange by remember {
        mutableStateOf(
            (currentFilters.minPrice ?: defaultMinPrice)..(currentFilters.maxPrice ?: defaultMaxPrice)
        )
    }


    val defaultMinEco = 0f
    val defaultMaxEco = 100f
    var ecoScoreRange by remember {
        mutableStateOf(
            (currentFilters.minEcoScore?.toFloat() ?: defaultMinEco)..(currentFilters.maxEcoScore?.toFloat() ?: defaultMaxEco)
        )
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    val newMinPrice = if (
                        priceRange.start > defaultMinPrice ||
                        priceRange.endInclusive < defaultMaxPrice
                    ) priceRange.start else null

                    val newMaxPrice = if (
                        priceRange.start > defaultMinPrice ||
                        priceRange.endInclusive < defaultMaxPrice
                    ) priceRange.endInclusive else null

                    // Only set ecoScore values if the range differs from default:
                    val newMinEco = if (
                        ecoScoreRange.start > defaultMinEco ||
                        ecoScoreRange.endInclusive < defaultMaxEco
                    ) ecoScoreRange.start.toInt() else null

                    val newMaxEco = if (
                        ecoScoreRange.start > defaultMinEco ||
                        ecoScoreRange.endInclusive < defaultMaxEco
                    ) ecoScoreRange.endInclusive.toInt() else null

                    val newFilters = FilterOptions(
                        brand = brand.trim(),
                        category = category,
                        sex = sex,
                        minPrice = newMinPrice,
                        maxPrice = newMaxPrice,
                        minEcoScore = newMinEco,
                        maxEcoScore = newMaxEco
                    )
                    onApply(newFilters)
                }
            ) {
                Text("Apply")
            }
        },
        dismissButton =  {
            TextButton(
                onClick = {
                    brand = ""
                    category = ""
                    sex = ""
                    priceRange = defaultMinPrice..defaultMaxPrice
                    ecoScoreRange = defaultMinEco..defaultMaxEco
                }
            ) {
                Text("Clear Filters")
            }
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Filter Apparel",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = { onDismiss() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.8f)
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Brand (free-text)
                TextFilterField(
                    label = "Brand",
                    value = brand,
                    onValueChange = { brand = it }
                )

                // Category (dropdown)
                DropdownFilterField(
                    label = "Category",
                    selected = category,
                    options = ApparelChoices.categories,
                    onOptionSelected = { category = it }
                )

                // Sex (dropdown)
                DropdownFilterField(
                    label = "Sex",
                    selected = sex,
                    options = ApparelChoices.sexes,
                    onOptionSelected = { sex = it }
                )


                // Price Range (numeric min/max)
                RangeFilter(
                    label = "Price Range (€)",
                    currentRange = priceRange,
                    onRangeChange = { priceRange = it },
                    valueBounds = defaultMinPrice..defaultMaxPrice,
                    steps = (defaultMaxPrice - defaultMinPrice).toInt()
                )

                // EcoScore Range (numeric min/max)
                RangeFilter(
                    label = "EcoScore",
                    currentRange = ecoScoreRange,
                    onRangeChange = { ecoScoreRange = it },
                    valueBounds = defaultMinEco..defaultMaxEco,
                    steps = (defaultMaxEco - defaultMinEco).toInt()
                )
            }
        }
    )
}