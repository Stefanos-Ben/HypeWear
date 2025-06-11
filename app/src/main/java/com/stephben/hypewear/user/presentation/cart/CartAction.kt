package com.stephben.hypewear.user.presentation.cart

sealed interface CartAction {
    data object OnEmptyCart: CartAction

    data class OnRemoveFromCart(val apparelId: String): CartAction

    data class OnSizeChange(val apparelId: String, val size: String): CartAction

    data class OnQuantityAdd(val apparelId: String): CartAction

    data class OnQuantitySubtract(val apparelId: String): CartAction
}