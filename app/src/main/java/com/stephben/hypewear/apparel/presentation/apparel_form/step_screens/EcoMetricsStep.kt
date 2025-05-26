package com.stephben.hypewear.apparel.presentation.apparel_form.step_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormAction
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormState
import com.stephben.hypewear.apparel.presentation.apparel_form.components.ErrorText
import com.stephben.hypewear.apparel.presentation.apparel_form.components.ExposedDropdown
import com.stephben.hypewear.apparel.presentation.apparel_form.components.MetricSlider
import com.stephben.hypewear.core.domain.utils.FabricLibrary
import com.stephben.hypewear.core.domain.utils.PackagingMaterials
import com.stephben.hypewear.core.presentation.components.BadgeDisplay

@Composable
fun EcoMetricsStep(
    state: ApparelFormState,
    onAction: (ApparelFormAction) -> Unit
) {

    val fabricLabels = FabricLibrary.items.mapValues { it.value.label }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .padding(top = 32.dp, bottom = 96.dp)
    ) {
        item {
            Text(
                text = "STEP 4: SUSTAINABILITY",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }

        item {
            ExposedDropdown(
                label = "Fabric",
                options = fabricLabels.values.toList(),
                selected = fabricLabels[state.fabricKey] ?: "",
                onSelect = { sel ->
                    val key = fabricLabels.entries.first { it.value == sel }.key
                    onAction(ApparelFormAction.OnFieldChanged("fabricKey", key))
                },
                modifier = Modifier.fillMaxWidth(),
                error = state.fieldErrors["fabricKey"],
                enabled = !state.isEdit
            )
            Spacer(Modifier.height(16.dp))
        }

        if (state.fabricKey == "Other") {
            item {
                OutlinedTextField(
                    value = state.customFabric,
                    onValueChange = {onAction(ApparelFormAction.OnFieldChanged("customFabric", it))},
                    label = { Text("Custom Fabric or Blend") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isEdit,
                )
                ErrorText(state.fieldErrors["customFabric"])
                Spacer(Modifier.height(16.dp))
            }

        }

        item {
            OutlinedTextField(
                value = state.apparelWeight,
                onValueChange = {onAction(ApparelFormAction.OnFieldChanged("apparelWeight", it))},
                label = { Text("Apparel weight (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = !state.isEdit,
                modifier = Modifier.fillMaxWidth(),
            )
            ErrorText(state.fieldErrors["apparelWeight"])
            Spacer(Modifier.height(16.dp))
        }

        item {
            MetricSlider(
                id = "higgMsi",
                label = "Higg MSI points",
                valueStr = state.higgMSI,
                valueRange = 5f..60f,
                step = 1f,
                suffix = " pts",
                enabled = !state.isEdit && state.fabricKey == "Other",
                onAction = onAction
            )
            ErrorText(state.fieldErrors["higgMsi"])
            Spacer(Modifier.height(16.dp))
        }

        item {
            MetricSlider(
                id = "carbonFootprint",
                label = "Carbon footprint",
                valueStr = state.carbonFootprint,
                valueRange = 0.1f..100f,
                step = 0.1f,
                suffix = " kg CO₂-eq",
                onAction = onAction,
                enabled = !state.isEdit
            )
            ErrorText(state.fieldErrors["carbonFootprint"])
            Spacer(Modifier.height(16.dp))
        }

        item {
            MetricSlider(
                id = "waterFootprint",
                label = "Water footprint",
                valueStr = state.waterFootprint,
                valueRange = 0f..5_000f,
                step = 100f,                    // each notch ≈ 1 000 L for usability
                suffix = " L",
                enabled = !state.isEdit,
                onAction = onAction
            )
            ErrorText(state.fieldErrors["waterFootprint"])
            Spacer(Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = state.packagingWeight,
                onValueChange = { onAction(ApparelFormAction.OnFieldChanged("packagingWeight", it))},
                label = { Text("Packaging weight(g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isEdit
            )
            ErrorText(state.fieldErrors["packagingWeight"])
            Spacer(Modifier.height(16.dp))
        }

        item {
            ExposedDropdown(
                label = "Packaging Material",
                options = PackagingMaterials.uiLabels.values.toList(),
                selected = PackagingMaterials.uiLabels[state.packagingMaterial] ?: "",
                onSelect = { sel ->
                    val key = PackagingMaterials.uiLabels.entries.first { it.value == sel }.key
                    onAction(ApparelFormAction.OnFieldChanged("packagingMaterial", key))
                },
                error = state.fieldErrors["packagingMaterial"],
                enabled = !state.isEdit,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = "Calculated eco-score: ${state.ecoScore} / 100",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(Modifier.height(16.dp))
            }

            if (state.ecoBadges.isNotEmpty()) { //TODO: CHANGE TO ICONS LATER
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.ecoBadges.forEach { badge ->
                        BadgeDisplay(
                            badge =  badge,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}