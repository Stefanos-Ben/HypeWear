package com.stephben.hypewear.apparel.presentation.tempadd

data class AddApparelState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: String = "0.0"
)
