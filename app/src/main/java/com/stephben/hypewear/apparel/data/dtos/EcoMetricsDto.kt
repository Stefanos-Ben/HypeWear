package com.stephben.hypewear.apparel.data.dtos

data class EcoMetricsDto(
    val materialSustainability: Double? = null,
    val carbonFootprint: Int? = null,
    val waterFootprint: Int? = null,
    val packagingSustainability: Double? = null,
)
