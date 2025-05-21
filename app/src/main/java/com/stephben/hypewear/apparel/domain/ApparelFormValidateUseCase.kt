package com.stephben.hypewear.apparel.domain

import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormState
import com.stephben.hypewear.apparel.presentation.apparel_form.FormStep

class ApparelFormValidateUseCase {
    operator fun invoke(step: FormStep, s: ApparelFormState): Map<String, String?> = when(step) {
        FormStep.PROFILE -> buildMap {
            if (s.description.isBlank()) put("description", "Required")
            if (!s.imageUrl.startsWith("http")) put("imageUrl", "URL must start with http")
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
            if (s.fabric.isBlank()) put("fabric", "Required")

            val cf = s.carbonFootprint.toDoubleOrNull()
            if (cf == null || cf !in 0.1..100.0) put("carbonFootprint", "0.1 - 100 kg")

            val wf = s.waterFootprint.toDoubleOrNull()
            if (wf == null || wf !in 1.0..100_000.0) put("waterFootprint", "1 - 100 000 L")

            val mat = s.preferredMaterialPct.toDoubleOrNull()
            if (mat == null || mat !in 0.0..100.0) put("preferredMaterialPct", "0 - 100 %")

            val pcr = s.packagingPCR.toDoubleOrNull()
            if (pcr == null || pcr !in 0.0..100.0) put("packagingPCR", "0 - 100 %")
        }

        else -> emptyMap()
    }
}