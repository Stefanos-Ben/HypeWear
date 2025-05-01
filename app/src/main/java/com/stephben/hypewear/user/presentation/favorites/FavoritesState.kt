package com.stephben.hypewear.user.presentation.favorites

import com.stephben.hypewear.apparel.domain.Apparel

data class FavoritesState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val favoriteIDs: List<String> = emptyList(),
    val favoriteApparels: List<Apparel> = emptyList()
)
