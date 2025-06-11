package com.stephben.hypewear.user.data.mappers

import com.stephben.hypewear.user.data.dtos.CartDto
import com.stephben.hypewear.user.domain.Cart

fun CartDto.toCart(): Cart = Cart(
    apparelId = this.apparelId ?: "",
    quantity = this.quantity ?: 1,
    size = this.size ?: ""
)

fun Cart.toDto(): CartDto = CartDto(
    apparelId = this.apparelId,
    quantity = this.quantity,
    size = this.size
)