package com.stephben.hypewear.apparel.presentation.apparel_form.step_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormAction
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormState
import com.stephben.hypewear.apparel.presentation.apparel_form.components.ExposedDropdown
import com.stephben.hypewear.core.domain.utils.ApparelChoices

@Composable
fun CategorizationStep(
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
            text = "STEP 2: CATEGORIZATION",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        ExposedDropdown(
            label = "Sex",
            options = ApparelChoices.sexes,
            selected = state.sex,
            onSelect = { onAction(ApparelFormAction.OnFieldChanged("sex", it)) },
            error = state.fieldErrors["sex"],
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        ExposedDropdown(
            label = "Category",
            options = ApparelChoices.categories,
            selected = state.category,
            onSelect = { onAction(ApparelFormAction.OnFieldChanged("category", it)) },
            error = state.fieldErrors["category"],
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Tags (optional)",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ApparelChoices.tags.forEach { tag ->
                FilterChip(
                    selected = tag in state.tags,
                    onClick = { onAction(ApparelFormAction.OnChipToggled("tags", tag)) },
                    label = { Text(tag) }
                )
            }
        }
    }
}