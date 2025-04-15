package com.stephben.hypewear.apparel.presentation.home_screen

import com.stephben.hypewear.apparel.domain.Apparel

data class HomeScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Apparel> = emptyList(),
    val newItems: List<Apparel> = emptyList(),
    val errorMessage : String? = null
)