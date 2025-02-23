package com.stephben.hypewear.apparel.presentation.apparel_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
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


class ApparelListViewModel(
    private val apparelRepository: ApparelRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state: MutableStateFlow<ApparelListState> = MutableStateFlow(ApparelListState())
    val state: StateFlow<ApparelListState> = _state.asStateFlow()
        .onStart {
            observeSearchQuery()
            onAction(ApparelListAction.GetApparels)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


    fun onAction(action: ApparelListAction) {
        reduce(action = action, oldState = state.value)
    }

    private fun setState(newState: ApparelListState) {
        _state.value = newState
    }

    private fun reduce(action: ApparelListAction, oldState: ApparelListState) {
        when (action) {

            ApparelListAction.GetApparels -> {
                getAllApparels(oldState)
            }

            is ApparelListAction.OnSearchQueryChange -> _state.update {
                it.copy(
                    searchQuery = action.query
                )
            }
        }
    }

    private fun getAllApparels(oldState: ApparelListState) {
        viewModelScope.launch {
            setState(
                oldState.copy(
                    isLoading = true
                )
            )

            when (val result = apparelRepository.getAllApparels()) {
                is Result.Failure -> {
                    val errorMessage =
                        result.exception.message ?: "An error occurred while fetching apparels"
                    setState(
                        oldState.copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    )
                    Log.d("FETCH ALL", "ERROR FETCHING ALL APPARELS: $errorMessage")
                }

                is Result.Success -> {
                    setState(
                        oldState.copy(
                            isLoading = false,
                            searchResults = result.data
                        )
                    )

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
                Log.d("SEARCH RESULT", "Found an eligible query")
                searchJob?.cancel()
                searchJob = searchApparels(query)
            }
            .launchIn(viewModelScope)
    }

    private fun searchApparels(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        when (val result = apparelRepository.searchApparels(query)) {
            is Result.Failure -> {
                val errorMessage =
                    result.exception.message ?: "An error occurred while fetching apparels"
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
                Log.d("SEARCH APPARELS", "ERROR SEARCHING APPARELS: $errorMessage")
            }

            is Result.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = result.data
                    )
                }
            }
        }
    }

}