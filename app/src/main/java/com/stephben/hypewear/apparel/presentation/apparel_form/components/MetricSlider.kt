package com.stephben.hypewear.apparel.presentation.apparel_form.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormAction
import kotlin.math.roundToInt

@Composable
fun MetricSlider(
    id: String,
    label: String,
    valueStr: String,
    valueRange: ClosedFloatingPointRange<Float>,
    step: Float = 1f,
    suffix: String = "",
    onAction: (ApparelFormAction) -> Unit
) {
    val current = valueStr.toFloatOrNull() ?: valueRange.start
    Text(
        text = "$label: ${current.roundToInt()}$suffix",
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge
    )
    Slider(
        value = current.coerceIn(valueRange),
        onValueChange = {
            val snapped = (it / step).roundToInt() * step
            onAction(ApparelFormAction.OnFieldChanged(id, snapped.toInt().toString()))
        },
        valueRange = valueRange,
        steps = ((valueRange.endInclusive - valueRange.start) / step).roundToInt() - 1,

    )

}