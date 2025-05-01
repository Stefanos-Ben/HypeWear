package com.stephben.hypewear.apparel.presentation.apparel_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
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
                getApparel(action.apparelId)
            }

            ApparelDetailAction.OnToggleFavorites -> {
                toggleFavorite()
            }

            ApparelDetailAction.OnCheckIsFavorite -> {
                isFavorite()
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

    private fun getApparel(apparelId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            when(val result = apparelRepository.getApparel(apparelId)){

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