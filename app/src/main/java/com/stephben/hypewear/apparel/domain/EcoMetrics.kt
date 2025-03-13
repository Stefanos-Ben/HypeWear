package com.stephben.hypewear.apparel.domain

data class EcoMetrics(
    val materialSustainability: Double = 0.0,
    val carbonFootprint: Int = 0,
    val waterFootprint: Int = 0,
    val packagingSustainability: Double = 0.0,
)
