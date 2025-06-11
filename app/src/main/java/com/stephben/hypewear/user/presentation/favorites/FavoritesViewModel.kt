package com.stephben.hypewear.user.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.Cart
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel (
    val userRepository: UserRepository,
    val apparelRepository: ApparelRepository,
): ViewModel() {

    private val tag = "FAVORITES VIEWMODEL"
    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    fun onAction(action: FavoritesAction){
        when(action){
            is FavoritesAction.OnLoadFavorites -> {
                loadUserFavorites()
            }

            is FavoritesAction.OnToggleFavorites -> {
                toggleFavorite(id = action.id, isFavorite = action.isFavorite)
            }

            FavoritesAction.OnLoadCart -> {
                loadCart()
            }
            is FavoritesAction.OnToggleCart -> {
                toggleCart(id = action.id, size = action.size, inCart = action.inCart)
            }
        }
    }

    private fun loadCart() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = userRepository.getUserCart()) {
            is Result.Success -> {
                _state.update { it.copy(cart = result.data.toSet()) }
            }

            is Result.Failure -> {
                _state.update { it.copy(isLoading = false) }
                Log.e(tag, "Error fetching user cart in user repo")
            }
        }
    }

    private fun toggleCart(id: String, size: String, inCart: Boolean) {
        viewModelScope.launch {
            if (!inCart){
                val updated = state.value.cart.plus(Cart(id, 1, size))
                modifyCart(updated)
            } else {
                val updated = state.value.cart.filterNot { it.apparelId == id }
                modifyCart(updated.toSet())
            }
        }
    }

    private fun modifyCart(newCart: Set<Cart>) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = userRepository.updateUserCart(newCart.toList())) {
            is Result.Success -> {
                loadCart()
            }
            is Result.Failure -> {
                Log.e(tag, result.exception.toString())
            }
        }
        _state.update { it.copy(isLoading = false) }
    }

    private fun toggleFavorite(id: String, isFavorite: Boolean) {
        Log.i(tag, "the state is $isFavorite")
        viewModelScope.launch {
            if (!isFavorite) {
                when (val result = userRepository.addUserFavorites(id)) {
                    is Result.Failure -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.exception.message
                                    ?: "Couldn't update favorites"
                            )
                        }
                        Log.d("FETCH DETAILS", "Couldn't update favorites")
                    }

                    is Result.Success -> {
                        loadUserFavorites()
                    }
                }
            } else {
                when (
                    val result = userRepository.removeUserFavorites(id)
                ) {
                    is Result.Failure -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.exception.message
                                    ?: "Couldn't update favorites"
                            )
                        }
                        Log.d("FETCH DETAILS", "Couldn't update favorites")
                    }

                    is Result.Success -> {
                        loadUserFavorites()
                    }
                }
            }
        }
    }

    private fun loadUserFavorites() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            when(val result = userRepository.getUserFavorites()){
                is Result.Failure -> {
                    val errorMessage = "Error fetching user's favorites IDs"
                    Log.e(tag, errorMessage + result.exception.message)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }
                    return@launch
                }

                is Result.Success -> {
                    Log.i(tag, "Fetched user's favorites IDs.")
                    _state.update {
                        it.copy(
                            favoriteIDs = result.data
                        )
                    }
                }

            }

            if (state.value.favoriteIDs.isNotEmpty()) {
                getFavoriteApparels(state.value.favoriteIDs)
            } else {
                val errorMessage  = "Empty IDs list stopping here"
                Log.e(tag, errorMessage)
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    private fun getFavoriteApparels(favoriteIDs: List<String>){
        viewModelScope.launch {
            when(val result = apparelRepository.getFavoriteApparels(favoriteIDs)){
                is Result.Failure -> {
                    Log.e(tag, "Couldn't fetch apparels from IDs: ${result.exception.message}")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                    }
                }

                is Result.Success -> {
                    Log.i(tag, "Fetched favorite apparels successfully!")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            favoriteApparels = result.data
                        )
                    }
                }
            }
        }
    }
}