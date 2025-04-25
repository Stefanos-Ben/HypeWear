package com.stephben.hypewear.apparel.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeScreenViewModel(
    private val apparelRepository: ApparelRepository
) : ViewModel() {

    //private var searchJob: Job? = null

    private val _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()
//        .onStart {
//            observeSearchQuery()
//        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


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
                    Log.d("FETCH ALL", "ERROR FETCHING ALL APPARELS: $errorMessage")
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            searchResults = result.data
                        )
                    }
                    Log.i("HOME VM", "Found ${result.data}")
                }
            }
        }
    }


//    @OptIn(FlowPreview::class)
//    private fun observeSearchQuery() {
//        state
//            .map { it.searchQuery }
//            .distinctUntilChanged()
//            .debounce(1000L)
//            .onEach { query ->
//                Log.d("SEARCH RESULT", "Found an eligible query")
//                searchJob?.cancel()
//                searchJob = searchApparels(query)
//            }
//            .launchIn(viewModelScope)
//    }

//    private fun searchApparels(query: String) = viewModelScope.launch {
//        _state.update {
//            it.copy(
//                isLoading = true
//            )
//        }
//
//        when (val result = apparelRepository.searchApparels(query)) {
//            is Result.Failure -> {
//                val errorMessage =
//                    result.exception.message ?: "An error occurred while fetching apparels"
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = errorMessage
//                    )
//                }
//                Log.d("SEARCH APPARELS", "ERROR SEARCHING APPARELS: $errorMessage")
//            }
//
//            is Result.Success -> {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        searchResults = result.data
//                    )
//                }
//            }
//        }
//    }

}