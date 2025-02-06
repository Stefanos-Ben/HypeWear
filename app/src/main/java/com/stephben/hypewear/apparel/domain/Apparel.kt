package com.stephben.hypewear.apparel.domain

data class Apparel(
    val apparelID: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: Double = 0.0,
    val currency: String = "€",
    val createdAt: String = "",
    val updatedAt: String = ""
)
