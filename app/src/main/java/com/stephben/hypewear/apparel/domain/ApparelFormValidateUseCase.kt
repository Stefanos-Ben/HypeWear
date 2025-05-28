package com.stephben.hypewear.apparel.domain

import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormState
import com.stephben.hypewear.apparel.presentation.apparel_form.FormStep
import com.stephben.hypewear.core.domain.utils.PackagingMaterials

class ApparelFormValidateUseCase {
    operator fun invoke(step: FormStep, s: ApparelFormState): Map<String, String?> = when(step) {
        FormStep.PROFILE -> buildMap {
            if (s.description.isBlank()) put("description", "Required")
            if (s.color.isBlank()) put("color", "Required")
        }

        FormStep.CATEGORIZATION -> buildMap {
            if (s.sex.isBlank()) put("sex", "Pick one")
            if (s.category.isBlank()) put("category", "Pick one")
        }

        FormStep.PRICE_STOCK -> buildMap {
            if (s.price.toDoubleOrNull() == null) put("price", "Enter a number!")
            if (s.stockPerSize.isEmpty()) put("stockPerSize", "Add at least one size")
            if (s.stockPerSize.values.any { it.toIntOrNull() == null })
                put("stockPerSize", "Quantities must be integers")
        }

        FormStep.MANUFACTURING_METRICS -> buildMap {
            if (s.fabricKey == "Other" && s.customFabric.isBlank())
                put("customFabric", "Describe the blend")

            /* garment weight */
            val gw = s.apparelWeight.toDoubleOrNull()
            if (gw == null || gw <= 0.0) put("apparelWeight", "Positive number (g)")

            /* Higg MSI – only user-editable in “Other” mode */
            val msi = s.higgMSI.toDoubleOrNull()
            if (s.fabricKey == "Other" && (msi == null || msi !in 5.0..60.0))
                put("higgMsi", "5 – 60 points")

            /* carbon */
            val cf = s.carbonFootprint.toDoubleOrNull()
            if (cf == null || cf !in 0.0..40.0)
                put("carbonFootprint", "0 – 40 kg")

            /* water */
            val wf = s.waterFootprint.toDoubleOrNull()
            if (wf == null || wf !in 0.0..5_000.0)
                put("waterFootprint", "0 – 5 000 L")

            /* packaging */
            val pw = s.packagingWeight.toDoubleOrNull()
            if (pw == null || pw < 0.0)
                put("packagingWeight", "≥ 0 g")

            if (s.packagingMaterial !in PackagingMaterials.uiLabels.keys)
                put("packagingMaterial", "Pick one")
        }

        else -> emptyMap()
    }
}