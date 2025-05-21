package com.stephben.hypewear.apparel.presentation.apparel_form.step_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormAction
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormState
import com.stephben.hypewear.apparel.presentation.apparel_form.components.ErrorText
import com.stephben.hypewear.apparel.presentation.apparel_form.components.MetricSlider

@Composable
fun EcoMetricsStep(
    state: ApparelFormState,
    onAction: (ApparelFormAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .padding(top = 32.dp)
    ) {

        Text(
            text = "STEP 4: METRICS",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.fabric,
            onValueChange = { onAction(ApparelFormAction.OnFieldChanged("fabric", it)) },
            label = { Text("Main fabric") },
            isError = state.fieldErrors["fabric"] != null,
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(state.fieldErrors["fabric"])

        Spacer(Modifier.height(16.dp))


        MetricSlider(
            id = "carbonFootprint",
            label = "Carbon footprint",
            valueStr = state.carbonFootprint,
            valueRange = 0.1f..100f,
            step = 0.1f,
            suffix = " kg CO₂-eq",
            onAction = onAction
        )
        ErrorText(state.fieldErrors["carbonFootprint"])

        Spacer(Modifier.height(16.dp))

        MetricSlider(
            id = "waterFootprint",
            label = "Water footprint",
            valueStr = state.waterFootprint,
            valueRange = 1f..100_000f,
            step = 1_000f,                    // each notch ≈ 1 000 L for usability
            suffix = " L",
            onAction = onAction
        )
        ErrorText(state.fieldErrors["waterFootprint"])

        Spacer(Modifier.height(16.dp))

        MetricSlider(
            id = "preferredMaterialPct",
            label = "Preferred material %",
            valueStr = state.preferredMaterialPct,
            valueRange = 0f..100f,
            suffix = " %",
            onAction = onAction
        )
        ErrorText(state.fieldErrors["preferredMaterialPct"])

        Spacer(Modifier.height(16.dp))

        MetricSlider(
            id = "packagingPCR",
            label = "Packaging PCR %",
            valueStr = state.packagingPCR,
            valueRange = 0f..100f,
            suffix = " %",
            onAction = onAction
        )
        ErrorText(state.fieldErrors["packagingPCR"])

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.packagingRecyclable,
                onCheckedChange = {
                    onAction(
                        ApparelFormAction.OnFieldChanged("packagingRecyclable", it.toString())
                    )
                }
            )
            Text(
                text = "Packaging recyclable",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = "Calculated eco-score: ${state.ecoScore} / 80",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp)
            )
        }


    }
}