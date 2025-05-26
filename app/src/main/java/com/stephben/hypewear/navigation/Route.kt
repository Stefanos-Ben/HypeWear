package com.stephben.hypewear.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Splash: Route

    @Serializable
    data object MainGraph: Route {
        @Serializable
        data object  HomeScreen: Route

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

    @Serializable
    data object AuthGraph: Route {
        @Serializable
        data object SignIn: Route

        @Serializable
        data object SignUp: Route

        @Serializable
        data object BrandSignUp: Route

        @Serializable
        data object ForgotPassword: Route

        @Serializable
        data class EmailVerification(val userType: String? = null): Route
    }

    @Serializable
    data object BrandGraph: Route {
        @Serializable
        data object BrandHome: Route

        @Serializable
        data object Collection: Route

        @Serializable
        data object Profile: Route

        @Serializable
        data class AddApparel(val id: String? = null): Route
    }
}