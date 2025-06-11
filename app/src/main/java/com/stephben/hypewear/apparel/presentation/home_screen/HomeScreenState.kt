package com.stephben.hypewear.apparel.presentation.home_screen

import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.user.domain.Cart

data class HomeScreenState(
    val isLoading: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val cart: Set<Cart> = emptySet(),
    val searchQuery: String = "",
    val searchResults: List<Apparel> = emptyList(),
    val newItems: List<Apparel> = emptyList(),
    val sustainableOfTheDay: Apparel = Apparel(),
    val errorMessage : String? = null
)