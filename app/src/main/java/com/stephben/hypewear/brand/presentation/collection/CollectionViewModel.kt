package com.stephben.hypewear.brand.presentation.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val brandRepository: BrandRepository,
    private val apparelRepository: ApparelRepository
): ViewModel() {
    private val tag = "COLLECTION VM"

    private val _state: MutableStateFlow<CollectionState> = MutableStateFlow(CollectionState())
    val state: StateFlow<CollectionState> = _state.asStateFlow()

    fun onAction(action: CollectionAction) {
        when (action) {
            is CollectionAction.GetApparels -> {
                getBrandApparels()
            }

            is CollectionAction.OnDelete -> {
                deleteApparel(action.id)
            }
        }
    }

    private fun getBrandApparels() {
         viewModelScope.launch {
             _state.update { it.copy(isLoading = true) }
             val currentBrandId = getCurrentBrandId()
             if (currentBrandId != null) {
                 when (val result = apparelRepository.getBrandApparels(currentBrandId)) {
                     is Result.Success -> _state.update {
                         it.copy(
                             apparels = result.data,
                             isLoading = false
                         )
                     }

                     is Result.Failure -> _state.update {
                         it.copy(
                             errorMessage = result.exception.message.toString(),
                             isLoading = false
                         )
                     }
                 }
             }
         }
    }

    private fun deleteApparel(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = apparelRepository.deleteApparel(id)) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                }
                is Result.Failure -> {
                    _state.update {
                        it.copy(
                            errorMessage = result.exception.message.toString(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private suspend fun getCurrentBrandId(): String? {
      return when (val result = brandRepository.getCurrentBrand()) {
            is Result.Success -> result.data.id
            is Result.Failure -> null
      }
    }
}
