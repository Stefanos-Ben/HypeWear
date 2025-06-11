package com.stephben.hypewear.user.presentation.cart

import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.user.domain.Cart

data class CartState(
    val userCart: List<Cart> = emptyList(),
    val cartApparels: List<Apparel> = emptyList(),
    val isLoading: Boolean = false

)
