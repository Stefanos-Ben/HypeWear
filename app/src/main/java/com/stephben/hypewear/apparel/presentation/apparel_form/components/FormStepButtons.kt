package com.stephben.hypewear.apparel.presentation.apparel_form.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stephben.hypewear.apparel.presentation.apparel_form.FormStep
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun FormStepButtons(
    modifier: Modifier = Modifier,
    isFirstStep: Boolean,
    isLastStep: Boolean,
    nextStep: () -> Unit,
    previousStep: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = { previousStep() }
        ) {
            Text(
                if (isFirstStep) "Cancel" else "Previous"
            )
        }
        Button(
            onClick = { nextStep() }
        ) {
            Text(
                if (isLastStep) "Done" else "Continue"
            )
        }
    }
}


@Preview
@Composable
private fun FormStepButtonsPrev() {
    HypeWearTheme {
        FormStepButtons(
            isFirstStep = true,
            isLastStep = FormStep.PROFILE.ordinal == FormStep.entries.size - 1,
            nextStep = {},
            previousStep = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}