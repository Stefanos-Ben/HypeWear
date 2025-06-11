package com.stephben.hypewear.apparel.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.Cart
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeScreenViewModel(
    private val apparelRepository: ApparelRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val tag = "HOME VIEWMODEL"

    private val _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    init {
        loadCart()
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.GetApparels -> {
                getAllApparels()
            }

            HomeScreenAction.GetNewApparels -> {
                getNewApparels()
            }

            is HomeScreenAction.OnSearchQueryChange -> _state.update {
                it.copy(
                    searchQuery = action.query
                )
            }

            HomeScreenAction.OnLoadFavorites -> {
                loadUserFavorites()
            }

            is HomeScreenAction.OnToggleFavorites -> {
                toggleFavorite(id = action.id, isFavorite = action.isFavorite)
            }

            HomeScreenAction.OnLoadSustainable -> {
                loadSustainable()
            }

            HomeScreenAction.OnLoadCart -> {
                loadCart()
            }
            is HomeScreenAction.OnToggleCart -> {
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

    private fun loadSustainable() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            when (val result = apparelRepository.getMaxEcoScore()) {
                is Result.Failure -> {
                    val errorMessage =
                        result.exception.message ?: "An error occurred while fetching apparels"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            sustainableOfTheDay = result.data
                        )
                    }
                }
            }
        }
    }

    private fun toggleFavorite(id: String, isFavorite: Boolean) {
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

    private fun getNewApparels() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            when (val result = apparelRepository.getNewApparels()) {
                is Result.Failure -> {
                    val errorMessage =
                        result.exception.message ?: "An error occurred while fetching apparels"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            newItems = result.data
                        )
                    }
                }
            }
        }
    }


    private fun getAllApparels() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }


            when (val result = apparelRepository.getAllApparels()) {
                is Result.Failure -> {
                    val errorMessage =
                        result.exception.message ?: "An error occurred while fetching apparels"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }
                    Log.d(tag, "ERROR FETCHING ALL APPARELS: $errorMessage")
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            searchResults = result.data
                        )
                    }
                    Log.i(tag, "Found ${result.data}")
                }
            }
        }
    }

    private fun loadUserFavorites() {
        viewModelScope.launch {
            when (val result = userRepository.getUserFavorites()) {
                is Result.Failure -> {
                    val errorMessage = "Error fetching user's favorites IDs"
                    Log.e(tag, errorMessage + result.exception.message)
                    _state.update {
                        it.copy(
                            errorMessage = errorMessage
                        )
                    }
                    return@launch
                }

                is Result.Success -> {
                    Log.i(tag, "Fetched user's favorites IDs.")
                    _state.update {
                        it.copy(
                            favorites = result.data.toSet(),
                        )
                    }
                }

            }
        }

    }
}