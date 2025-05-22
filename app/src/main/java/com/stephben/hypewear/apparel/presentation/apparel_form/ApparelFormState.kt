package com.stephben.hypewear.apparel.presentation.apparel_form

import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.EcoMetrics
import com.stephben.hypewear.core.domain.utils.FabricLibrary
import com.stephben.hypewear.core.domain.utils.PackagingMaterials

data class ApparelFormState(
    val step: FormStep = FormStep.PROFILE,

    // ── PROFILE ──────────────────────────────────────────────
    val description: String = "",
    val imageUrl: String = "",
    val color: String = "",

    // ── Fabric ──────────────────────────────────────────────
    val fabricKey: String = FabricLibrary.items.keys.first(),
    val customFabric: String = "",
    val apparelWeight: String = "",

    // ── CATEGORIZATION ──────────────────────────────────────
    val sex: String = "",
    val category: String = "",
    val tags: Set<String> = emptySet(),

    // ── PRICE + STOCK ───────────────────────────────────────
    val price: String = "",
    val stockPerSize: Map<String, String> = emptyMap(),

    // ── ECO ────────────────────────────────
    val higgMSI: String = "",   // 5-60 pts
    val carbonFootprint: String = "",   // kg CO2-eq (0-40)
    val waterFootprint: String = "",    // litres (0-5000)
    val packagingWeight: String = "",   // g of primary packaging
    val packagingMaterial: String = PackagingMaterials.VIRGIN_PLASTIC,

    val ecoScore: Int = 0,
    val ecoBadges: Set<String> = emptySet(),

    // ── META ────────────────────────────────────────────────
    val fieldErrors: Map<String, String?> = emptyMap(),
    val isLoading: Boolean = false,
    val completed: Boolean = false,
)



