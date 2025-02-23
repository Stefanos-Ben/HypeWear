package com.stephben.hypewear.apparel.presentation.tempadd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AddApparelViewModel(
    private val repository: ApparelRepository
) : ViewModel() {

    private val _state: MutableStateFlow<AddApparelState> = MutableStateFlow(AddApparelState())
    val state: StateFlow<AddApparelState> = _state.asStateFlow()

    fun onAction(action: AddApparelAction) {
        reduce(action = action)
    }

    private fun reduce(action: AddApparelAction) {
        when (action) {
            is AddApparelAction.OnAddSubmit -> addApparel(
                Apparel(
                    title = state.value.title,
                    description = state.value.description,
                    price = state.value.price.toDouble(),
                    imageUrl = state.value.imageUrl,
                )
            )
            is AddApparelAction.OnDescriptionChange -> _state.update {
                it.copy(
                    description = action.description
                )
            }

            is AddApparelAction.OnImageUrlChange -> _state.update {
                it.copy(
                    imageUrl = action.imageUrl
                )
            }

            is AddApparelAction.OnPriceChange -> _state.update {
                it.copy(
                    price = action.price.toDouble().toString()
                )
            }

            is AddApparelAction.OnTitleChange -> _state.update {
                it.copy(
                    title = action.title
                )
            }
        }
    }

    private fun addApparel(apparel: Apparel) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }


            when (
                val result = repository.createApparel(
                    title = apparel.title,
                    description = apparel.description,
                    imageUrl = apparel.imageUrl,
                    price = apparel.price
                )) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            title = "",
                            description = "",
                            imageUrl = "",
                            price = ""
                        )
                    }
                    Log.d("FIREBASE ADD", "Added ${result.data}")
                }

                is Result.Failure -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    Log.d("FIREBASE ADD", "ERROR: ${result.exception}")
                }
            }
        }
    }
}