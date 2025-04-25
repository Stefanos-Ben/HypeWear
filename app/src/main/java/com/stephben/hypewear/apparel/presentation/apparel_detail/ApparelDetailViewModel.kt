package com.stephben.hypewear.apparel.presentation.apparel_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApparelDetailViewModel(
    private val repository: ApparelRepository
): ViewModel() {

    private val _state = MutableStateFlow(ApparelDetailState())

    val state = _state


    fun onAction(action: ApparelDetailAction) {
        when(action){

            is ApparelDetailAction.OnSelectedApparelChange -> {
                getApparel(action.apparelId)
            }
        }
    }

    private fun getApparel(apparelId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            when(val result = repository.getApparel(apparelId)){

                is Result.Failure -> {
                    val errorMessage =
                        result.exception.message ?: "An error occurred while fetching apparels"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }
                    Log.d("FETCH DETAILS", "ERROR FETCHING AN APPAREL: $errorMessage")
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            apparel = result.data
                        )
                    }
                }

            }
        }
    }
}