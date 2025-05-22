package com.stephben.hypewear.core.domain.utils

object PackagingMaterials {
    const val VIRGIN_PLASTIC = "VirginPlastic"
    const val VIRGIN_PAPER = "VirginPaper"
    const val RECYCLED_PLASTIC_FSC_PAPER = "RecycledPlasticOrFSCPaper"
    const val HIGH_RECYCLED_OR_COMPOSTABLE = "HighRecycledOrCompostable"

    val factors = mapOf(
        VIRGIN_PLASTIC to 1.0,
        VIRGIN_PAPER to 0.7,
        RECYCLED_PLASTIC_FSC_PAPER to 0.5,
        HIGH_RECYCLED_OR_COMPOSTABLE to 0.2,
    )

    val uiLabels = mapOf(
        VIRGIN_PLASTIC to "Virgin plastic (polybag)",
        VIRGIN_PAPER to "Virgin paper / cardboard",
        RECYCLED_PLASTIC_FSC_PAPER to "≥30  % recycled plastic / FSC paper",
        HIGH_RECYCLED_OR_COMPOSTABLE to "≥70 % recycled / compostable",
    )
}