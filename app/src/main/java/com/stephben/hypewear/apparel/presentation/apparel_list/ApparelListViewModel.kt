package com.stephben.hypewear.apparel.presentation.apparel_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.core.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApparelListViewModel(
    private val apparelRepository: ApparelRepository
): ViewModel() {

    private val _apparels = MutableStateFlow<List<Apparel>>(emptyList())
    val apparels: StateFlow<List<Apparel>> = _apparels

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    init {
        fetchAllApparels()
    }

    private fun fetchAllApparels() {
        viewModelScope.launch {
            when(val result = apparelRepository.getAllApparels()) {
                is Result.Success -> {
                    _apparels.value = result.data
                }
                is Result.Failure -> {
                    _errorMessage.value = result.exception.message ?: "An unknown error occurred"
                }
            }
        }
    }
}