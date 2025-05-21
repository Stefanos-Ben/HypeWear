package com.stephben.hypewear.brand.presentation.brand_home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrandHomeViewModel(
    private val brandRepository: BrandRepository,
): ViewModel() {
    private val tag = "BRAND HOME VM"

    private val _state: MutableStateFlow<BrandHomeState> = MutableStateFlow(BrandHomeState())
    val state: StateFlow<BrandHomeState> = _state.asStateFlow()

    fun onAction(action: BrandHomeAction){
        when(action){
            BrandHomeAction.OnLoadBrand -> {
                loadCurrentBrand()
            }
        }
    }

    private fun loadCurrentBrand() {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when(val result = brandRepository.getCurrentBrand()) {
                is Result.Failure -> {
                    val errorMessage = result.exception.message
                    Log.e(tag, "Error: $errorMessage")
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
                            brand = result.data
                        )
                    }
                }
            }
        }
    }
}