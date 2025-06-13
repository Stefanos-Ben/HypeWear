package com.stephben.hypewear.apparel.presentation.apparel_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.Cart
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApparelDetailViewModel(
    private val apparelRepository: ApparelRepository,
    private val userRepository: UserRepository,
): ViewModel() {

    private val _state = MutableStateFlow(ApparelDetailState())
    val state = _state


    fun onAction(action: ApparelDetailAction) {
        when(action){
            is ApparelDetailAction.OnSelectedApparelChange -> {
                loadApparelAndCart(action.apparelId)
            }
            ApparelDetailAction.OnToggleFavorites -> { toggleFavorite() }
            ApparelDetailAction.OnCheckIsFavorite -> { isFavorite() }
            is ApparelDetailAction.OnSizeSelected -> selectSize(action.size)
            ApparelDetailAction.OnAddToCart -> addToCart()
            ApparelDetailAction.OnQuantityAdd -> changeQuantity(delta = 1)
            ApparelDetailAction.OnQuantitySubtract -> changeQuantity(delta = -1)
        }
    }

    private fun addToCart() = viewModelScope.launch {
        val apparelId = _state.value.apparel!!.apparelID
        val newItem = Cart(
            apparelId = apparelId,
            quantity = 1,
            size = _state.value.selectedSize
        )
        val currentCart = (userRepository.getUserCart() as? Result.Success)?.data?.toMutableList() ?: mutableListOf()
        currentCart.add(newItem)
        when (val res = userRepository.updateUserCart(currentCart)) {
            is Result.Success -> _state.update { it.copy(cartQuantity = 1) }
            is Result.Failure -> Log.e("ApparelDetailVM", "Failed to add to cart", res.exception)
        }
    }

    private fun selectSize(size: String) = viewModelScope.launch {
        if (_state.value.cartQuantity > 0) {
            val updatedCart = (userRepository.getUserCart() as? Result.Success)?.data?.toMutableList() ?: mutableListOf()
            updatedCart.indexOfFirst { it.apparelId == _state.value.apparel!!.apparelID }.takeIf { it >= 0 }?.let { idx ->
                val item = updatedCart[idx]
                updatedCart[idx] = item.copy(size = size)
                when (val res = userRepository.updateUserCart(updatedCart)) {
                    is Result.Success -> _state.update { it.copy(selectedSize = size) }
                    is Result.Failure -> Log.e("ApparelDetailVM", "Failed to update cart size", res.exception)
                }
            }
        } else {
            _state.update { it.copy(selectedSize = size) }
        }
    }

    private fun changeQuantity(delta: Int) = viewModelScope.launch {
        val apparelId = _state.value.apparel!!.apparelID
        val currentCart = (userRepository.getUserCart() as? Result.Success)?.data?.toMutableList() ?: mutableListOf()
        currentCart.firstOrNull { it.apparelId == apparelId }?.let { item ->
            val newQty = item.quantity + delta
            val updatedCart = if (newQty > 0) {
                currentCart.apply {
                    val idx = indexOf(item)
                    set(idx, item.copy(quantity = newQty))
                }
            } else {
                currentCart.apply { remove(item) }
            }
            when (val res = userRepository.updateUserCart(updatedCart)) {
                is Result.Success -> _state.update { it.copy(cartQuantity = if (newQty > 0) newQty else 0) }
                is Result.Failure -> Log.e("ApparelDetailVM", "Failed to change quantity", res.exception)
            }
        }
    }

    private fun isFavorite() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isFavorite = userRepository.isFavorite(_state.value.apparel!!.apparelID)
                )
            }
        }
    }

    private fun toggleFavorite() {
        Log.i("TRIGGER FAV", "the state is ${_state.value.isFavorite}")
        viewModelScope.launch {
            if (!_state.value.isFavorite){
                when(val result = userRepository.addUserFavorites(_state.value.apparel!!.apparelID)){
                    is Result.Failure -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.exception.message
                                    ?:"Couldn't update favorites"
                            )
                        }
                        Log.d("FETCH DETAILS", "Couldn't update favorites")
                    }

                    is Result.Success -> {
                        isFavorite()
                    }
                }
            } else {
                when(
                    val result = userRepository.removeUserFavorites(state.value.apparel!!.apparelID)
                ){
                    is Result.Failure -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.exception.message
                                    ?:"Couldn't update favorites"
                            )
                        }
                        Log.d("FETCH DETAILS", "Couldn't update favorites")
                    }

                    is Result.Success -> {
                        isFavorite()
                    }
                }
            }
        }
    }

    private fun loadApparelAndCart(apparelId: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = apparelRepository.getApparel(apparelId)) {
            is Result.Success -> {
                val apparel = result.data
                val defaultSize = apparel.stockPerSize.keys.firstOrNull().orEmpty()
                var quantity = 0
                var initialSize = defaultSize
                when (val cartRes = userRepository.getUserCart()) {
                    is Result.Success -> {
                        cartRes.data.firstOrNull { it.apparelId == apparel.apparelID }?.let {
                            quantity = it.quantity
                            initialSize = it.size
                        }
                    }
                    is Result.Failure -> Log.e("ApparelDetailVM", "Failed to load cart", cartRes.exception)
                }
                _state.update {
                    it.copy(
                        isLoading = false,
                        apparel = apparel,
                        selectedSize = initialSize,
                        cartQuantity = quantity
                    )
                }
                _state.value.apparel?.apparelID?.let { onAction(ApparelDetailAction.OnCheckIsFavorite) }
            }
            is Result.Failure -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exception.message ?: "Error fetching product"
                    )
                }
            }
        }
    }
}