package com.stephben.hypewear.apparel.presentation.tempadd

import com.stephben.hypewear.brand.domain.Brand

data class AddApparelState(
    val isLoading: Boolean = false,

    val description: String = "",
    val imageUrl: String = "",

    val price: String = "0.0",
    val currency: String = "€",
    val discount: String = "0.0",

    val brands: List<Brand> = emptyList(),
    val selectedBrand: Brand? = null,


    val color: String = "",
    val fabric: String = "",

    val materialSustainability: String = "0.0",
    val carbonFootprint: String = "0",
    val waterFootprint: String = "0",
    val packagingSustainability: String = "0.0",
    val ecoScore: String = "0",
    val ecoBadges: String = "",
)
