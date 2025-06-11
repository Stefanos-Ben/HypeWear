package com.stephben.hypewear.user.presentation.favorites

import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.user.domain.Cart

data class FavoritesState(
    val isLoading: Boolean = false,
    val cart: Set<Cart> = emptySet(),
    val errorMessage: String? = null,
    val favoriteIDs: List<String> = emptyList(),
    val favoriteApparels: List<Apparel> = emptyList()
)
