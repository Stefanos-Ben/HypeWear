package com.stephben.hypewear.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object ApparelGraph: Route

    @Serializable
    data object  ApparelList: Route

    @Serializable
    data class ApparelDetail(val id: String) : Route

    @Serializable
    data class ApparelSearch(val query: String): Route

    @Serializable
    data object Favorites: Route

    @Serializable
    data object Profile: Route

    @Serializable
    data object CreateApparelTemp: Route
}