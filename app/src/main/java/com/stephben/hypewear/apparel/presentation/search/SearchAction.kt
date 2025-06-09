package com.stephben.hypewear.apparel.presentation.search

import com.stephben.hypewear.apparel.presentation.search.components.FilterOptions

sealed interface SearchAction {
    data class OnSearchQueryChange(val query: String): SearchAction

    data class OnFiltersApplied(val filters: FilterOptions) : SearchAction

    data class OnToggleFavorites(val id: String, val isFavorite: Boolean): SearchAction

    data object OnFiltersCanceled : SearchAction

    data object OnLoadFavorites: SearchAction
}