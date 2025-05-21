package com.stephben.hypewear.apparel.presentation.apparel_form

import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.EcoMetrics

data class ApparelFormState(
    val step: FormStep = FormStep.PROFILE,

    // ── PROFILE ──────────────────────────────────────────────
    val description: String = "",
    val imageUrl: String = "",
    val color: String = "",

    // ── CATEGORIZATION ──────────────────────────────────────
    val sex: String = "",
    val category: String = "",
    val tags: Set<String> = emptySet(),

    // ── PRICE + STOCK ───────────────────────────────────────
    val price: String = "",
    val stockPerSize: Map<String, String> = emptyMap(),

    // ── MANUFACTURING / ECO ────────────────────────────────
    val fabric: String = "",

    val carbonFootprint: String = "",      // kg CO₂-eq
    val waterFootprint: String = "",       // litres
    val preferredMaterialPct: String = "", // 0-100 %
    val packagingPCR: String = "",         // 0-100 %
    val packagingRecyclable: Boolean = false,

    val ecoScore: Int = 0,
    val ecoBadges: Set<String> = emptySet(),

    // ── Meta ────────────────────────────────────────────────
    val fieldErrors: Map<String, String?> = emptyMap(),
    val isLoading: Boolean = false,
    val completed: Boolean = false,
)



