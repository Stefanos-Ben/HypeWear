package com.stephben.hypewear.core.domain.utils

fun sizeOptions(sex: String, category: String): List<String> = when (category) {

    /* ------- TOPS ------- */
    "T-shirt", "Hoodie", "Sweatshirt", "Jacket", "Dress", "Sweater" ->
        when (sex) {
            "Men", "Women", "Unisex" -> SizeCharts.alphaTops
            "Kids" -> SizeCharts.kidsTops
            else -> emptyList()
        }
    /* ------- BOTTOMS ------- */
    "Jeans", "Trousers", "Shorts", "Skirt" ->
        when (sex) {
            "Men", "Unisex" -> SizeCharts.menWaist
            "Women" -> SizeCharts.womenWaist
            else -> emptyList()
        }

    /* ------- SHOES ------- */
    "Sneakers", "Boots" -> SizeCharts.shoesEU

    /* ------- ACCESSORIES ------- */
    "Accessories" -> listOf("One Size")

    else -> emptyList()
}