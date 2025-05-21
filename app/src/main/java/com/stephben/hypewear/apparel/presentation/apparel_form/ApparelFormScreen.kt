package com.stephben.hypewear.apparel.presentation.apparel_form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.apparel.presentation.apparel_form.components.FormStepButtons
import com.stephben.hypewear.apparel.presentation.apparel_form.components.StepsProgressBar
import com.stephben.hypewear.apparel.presentation.apparel_form.step_screens.CategorizationStep
import com.stephben.hypewear.apparel.presentation.apparel_form.step_screens.EcoMetricsStep
import com.stephben.hypewear.apparel.presentation.apparel_form.step_screens.PriceStockStep
import com.stephben.hypewear.apparel.presentation.apparel_form.step_screens.ProfileStep
import com.stephben.hypewear.apparel.presentation.apparel_form.step_screens.ReviewStep
import com.stephben.hypewear.apparel.presentation.apparel_form.step_screens.SuccessScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ApparelFormScreen(
    viewModel: ApparelFormViewModel = koinViewModel(),
    onLeave: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val isFirstStep = state.step.ordinal == 0

    val isLastStep = state.step.ordinal == (FormStep.entries.size - 1)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            state.step == FormStep.PROFILE -> ProfileStep(state, viewModel::onAction)
            state.step == FormStep.CATEGORIZATION -> CategorizationStep(state, viewModel::onAction)
            state.step == FormStep.PRICE_STOCK -> PriceStockStep(state, viewModel::onAction)
            state.step == FormStep.MANUFACTURING_METRICS -> EcoMetricsStep(
                state,
                viewModel::onAction
            )
            state.step == FormStep.REVIEW && !state.completed -> ReviewStep(state, viewModel::onAction)
            state.completed -> SuccessScreen { onLeave() }
        }

        if (!state.completed){
            FormStepButtons(
                isFirstStep = isFirstStep,
                isLastStep = isLastStep,
                nextStep = {
                    if (isLastStep) {
                        viewModel.onAction(ApparelFormAction.OnSubmit)
                    } else {
                        viewModel.onAction(ApparelFormAction.OnNextClicked)
                    }
                },
                previousStep = {
                    if (isFirstStep) {
                        onLeave()
                    } else {
                        viewModel.onAction(ApparelFormAction.OnBackClicked)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 64.dp)
            )
        }


        StepsProgressBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp)
                .padding(bottom = 32.dp),
            currentStep = state.step.ordinal
        )


        if (state.isLoading) CircularProgressIndicator()
    }


}