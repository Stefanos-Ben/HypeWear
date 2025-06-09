package com.stephben.hypewear.apparel.presentation.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RangeFilter(
    label: String,
    currentRange: ClosedFloatingPointRange<Float>,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    valueBounds: ClosedFloatingPointRange<Float>,
    steps: Int = 0
) {
    Column {
        Text(
            text = "$label: ${currentRange.start.toInt()} - ${currentRange.endInclusive.toInt()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        RangeSlider(
            value = currentRange,
            onValueChange = onRangeChange,
            valueRange = valueBounds,
            steps = steps
        )
    }
}