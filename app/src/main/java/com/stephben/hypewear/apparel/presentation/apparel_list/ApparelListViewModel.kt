package com.stephben.hypewear.apparel.presentation.apparel_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApparelListViewModel @Inject constructor(
    private val apparelRepository: ApparelRepository
): ViewModel() {

    private val _state: MutableStateFlow<ApparelListState> = MutableStateFlow(ApparelListState())
    val state: StateFlow<ApparelListState> = _state.asStateFlow()


    init {
        onAction(ApparelListAction.GetApparels)
    }

    fun onAction(action: ApparelListAction) {
        reduce(action = action, oldState = state.value)
    }

    private fun setState(newState: ApparelListState) {
        _state.value = newState
    }

    private fun reduce(action: ApparelListAction, oldState: ApparelListState) {
        when(action) {

            ApparelListAction.GetApparels -> {
                getAllApparels(oldState)
            }
            is ApparelListAction.onSearchQueryChange -> TODO()
        }
    }

    private fun getAllApparels(oldState: ApparelListState) {
        Log.d("GET APPARELS FUNCTION", "I am inside")
        viewModelScope.launch {
            setState(
                oldState.copy(
                    isLoading = true
                )
            )

            when(val result = apparelRepository.getAllApparels()) {
                is Result.Failure -> {
                    val errorMessage =
                        result.exception.message ?: "An error occurred while fetching apparels"
                    setState(
                        oldState.copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    )
                    Log.d("INSIDE WHEN", "WHEN IS A FAILURE: $errorMessage")
                }
                is Result.Success -> {
                    setState(
                        oldState.copy(
                            isLoading = false,
                            searchResults = result.data
                        )
                    )
                    Log.d("INSIDE WHEN", "WHEN IS A SUCCESS!")
                }
            }
        }
    }

}