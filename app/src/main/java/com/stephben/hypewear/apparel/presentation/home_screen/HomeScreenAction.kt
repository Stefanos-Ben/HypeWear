package com.stephben.hypewear.apparel.presentation.home_screen

sealed interface HomeScreenAction {

    data object GetApparels : HomeScreenAction

    data object GetNewApparels: HomeScreenAction

    data class OnSearchQueryChange(val query: String): HomeScreenAction

    data object OnLoadFavorites: HomeScreenAction

    data class OnToggleFavorites(val id: String, val isFavorite: Boolean): HomeScreenAction
}