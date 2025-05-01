package com.stephben.hypewear.user.presentation.favorites

import com.stephben.hypewear.apparel.presentation.home_screen.HomeScreenAction

sealed interface FavoritesAction {
    data object OnLoadFavorites: FavoritesAction

    data class  OnToggleFavorites(val id: String, val isFavorite: Boolean): FavoritesAction
}