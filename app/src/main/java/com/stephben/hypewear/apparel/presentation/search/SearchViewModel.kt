package com.stephben.hypewear.apparel.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelFilters
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.apparel.presentation.search.components.FilterOptions
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.user.domain.Cart
import com.stephben.hypewear.user.domain.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val apparelRepository: ApparelRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val tag = "SEARCH VM"

    private var searchJob: Job? = null

    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()
        .onStart {
            observeSearchQuery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }

            is SearchAction.OnFiltersApplied -> {
                _state.update { it.copy(filterOptions = action.filters) }
                searchApparels(
                    query = _state.value.searchQuery,
                    filters = action.filters.toDomain()
                )
            }
            SearchAction.OnFiltersCanceled -> {
                _state.update { it.copy(filterOptions = FilterOptions()) }
            }

            SearchAction.OnLoadFavorites -> {
                loadUserFavorites()
            }

            is SearchAction.OnToggleFavorites -> {
                toggleFavorite(id = action.id, isFavorite = action.isFavorite)
            }

            SearchAction.OnLoadCart -> {
                loadCart()
            }
            is SearchAction.OnToggleCart -> {
                toggleCart(id = action.id, size = action.size, inCart = action.inCart)
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

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(1000L)
            .onEach { query ->
                val filters = _state.value.filterOptions.toDomain()
                Log.d(tag, "Found an eligible query")
                searchJob?.cancel()
                searchJob = searchApparels(query, filters)
            }.launchIn(viewModelScope)
    }

    private fun searchApparels(query: String, filters: ApparelFilters) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        when (val result = apparelRepository.searchApparels(query, filters)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        results = result.data
                    )
                }
            }

            is Result.Failure -> {
                val errorMessage =
                    result.exception.message ?: "An error occurred while fetching apparels"
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
                Log.e(tag, "ERROR SEARCHING APPARELS: $errorMessage")
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
}