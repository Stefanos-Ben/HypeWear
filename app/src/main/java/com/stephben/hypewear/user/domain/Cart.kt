package com.stephben.hypewear.user.domain

data class Cart(
    val apparelId: String = "",
    val quantity: Int = 0,
    val size: String = ""
)