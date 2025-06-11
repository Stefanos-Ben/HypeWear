package com.stephben.hypewear.apparel.presentation.search

import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.presentation.search.components.FilterOptions
import com.stephben.hypewear.user.domain.Cart

data class SearchState(
    val searchQuery: String = "",
    val filterOptions: FilterOptions = FilterOptions(),
    val cart: Set<Cart> = emptySet(),
    val results: List<Apparel> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
