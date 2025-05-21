package com.stephben.hypewear.apparel.presentation.apparel_form.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.apparel.presentation.apparel_form.FormStep
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun StepsProgressBar(
    modifier: Modifier = Modifier,
    numberOfSteps: Int = FormStep.entries.size,
    currentStep: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (step in 0..< numberOfSteps) {
            Step(
                modifier = Modifier.weight(if (step == 0) 1f else 4f),
                isComplete = step < currentStep,
                isCurrent = step == currentStep,
                isFirst = step == 0
            )
        }
    }
}

@Composable
fun Step(
    modifier: Modifier = Modifier,
    isComplete: Boolean,
    isCurrent: Boolean,
    isFirst: Boolean
) {
    val color = if (isComplete  || isCurrent) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

    val innerCircleColor = if (isComplete) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onSurface

    Box(modifier = modifier) {
        if (!isFirst){
            HorizontalDivider(
                modifier = Modifier.align(Alignment.CenterStart),
                color = color,
                thickness = 2.dp
            )
        }


        Canvas(
            modifier = Modifier
                .size(15.dp)
                .align(Alignment.CenterEnd)
                .border(
                    shape = CircleShape,
                    width = 2.dp,
                    color = color
                ),
            onDraw = {
                drawCircle(color = innerCircleColor)
            }
        )
    }

}

@Preview
@Composable
private fun StepProgressBarPrev() {
    HypeWearTheme {
        StepsProgressBar(
            numberOfSteps = FormStep.entries.size,
            currentStep = FormStep.REVIEW.ordinal
        )
    }
}