package com.stephben.hypewear.user.presentation.favorites

sealed interface FavoritesAction {
    data object OnLoadFavorites: FavoritesAction

    data class  OnToggleFavorites(val id: String, val isFavorite: Boolean): FavoritesAction

    data object OnLoadCart: FavoritesAction

    data class OnToggleCart(val id: String, val size: String, val inCart: Boolean): FavoritesAction
}