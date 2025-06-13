package com.stephben.hypewear.user.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.order.domain.OrderItem
import com.stephben.hypewear.order.domain.OrderRepository
import com.stephben.hypewear.user.domain.Cart
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CartViewModel(
    private val userRepository: UserRepository,
    private val apparelRepository: ApparelRepository,
    private val orderRepository: OrderRepository,
): ViewModel() {
    private val tag = "CART VIEWMODEL"
    private val _state: MutableStateFlow<CartState> = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    init {
        loadCart()
    }



    fun onAction(action: CartAction) = when (action) {
        CartAction.OnEmptyCart ->  modifyCart(emptyList())
        is CartAction.OnRemoveFromCart -> {
            val updated = state.value.userCart.filterNot { it.apparelId == action.apparelId }
            modifyCart(updated)
        }
        is CartAction.OnSizeChange -> {
            val updated = state.value.userCart.map {
                if (it.apparelId == action.apparelId) it.copy(size = action.size) else it
            }
            modifyCart(updated)
        }
        is CartAction.OnQuantityAdd -> {
            val updated = state.value.userCart.map {
                if (it.apparelId == action.apparelId) it.copy(quantity = it.quantity + 1) else it
            }
            modifyCart(updated)
        }
        is CartAction.OnQuantitySubtract -> {
            val updated = state.value.userCart.map {
                if (it.apparelId == action.apparelId && it.quantity > 1) it.copy(quantity = it.quantity - 1)
                else it
            }
            modifyCart(updated)
        }

        CartAction.OnCheckout -> {
            checkoutCart()
        }
    }

    private fun checkoutCart() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val result = userRepository.getCurrentUserDoc()
        if (result is Result.Success){
            val userId = result.data.userId
            val items = state.value.userCart.mapNotNull { cart ->
                state.value.cartApparels.find { it.apparelID == cart.apparelId }?.let { apparel ->
                    OrderItem(
                        apparelId = cart.apparelId,
                        size = cart.size,
                        quantity = cart.quantity,
                        price = apparel.price,
                        brandId = apparel.brand.id
                    )
                }
            }
            when (val orderResult = orderRepository.createOrder(userId, items)) {
                is Result.Success -> {
                    userRepository.updateUserCart(emptyList())
                    loadCart()
                }
                is Result.Failure -> {
                    Log.e("CART_VM", "Problem creating order: ", orderResult.exception)
                }
            }
        }
        _state.update { it.copy(isLoading = false) }
    }

    private fun modifyCart(newCart: List<Cart>) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = userRepository.updateUserCart(newCart)) {
            is Result.Success -> {
                loadCart()
            }
            is Result.Failure -> {
                Log.e(tag, result.exception.toString())
            }
        }
        _state.update { it.copy(isLoading = false) }
    }


    private fun loadCart() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = userRepository.getUserCart()) {
            is Result.Success -> { _state.update { it.copy(userCart = result.data) } }
            is Result.Failure -> {
                _state.update { it.copy(isLoading = false) }
                Log.e(tag, "Error fetching user cart in user repo")
            }
        }

        when (val result = apparelRepository.getFavoriteApparels(_state.value.userCart.map { it.apparelId })) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        cartApparels = result.data
                    )
                }
            }

            is Result.Failure -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        userCart = emptyList()
                    )
                }
                Log.e(tag, "Error fetching user cart in apparel repo")
            }
        }
    }
}