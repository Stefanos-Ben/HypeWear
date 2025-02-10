package com.stephben.hypewear.apparel.presentation.apparel_list

import com.stephben.hypewear.apparel.domain.Apparel

data class ApparelListState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Apparel> = emptyList(),
    val errorMessage : String? = null
)