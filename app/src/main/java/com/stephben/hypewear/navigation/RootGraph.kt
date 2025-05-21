package com.stephben.hypewear.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stephben.hypewear.core.presentation.splash_screen.SplashScreen

import com.stephben.hypewear.navigation.auth.authGraph
import com.stephben.hypewear.navigation.brand.brandGraph
import com.stephben.hypewear.navigation.main.mainGraph

@Composable
fun RootGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash
    ){

        composable<Route.Splash> {
            SplashScreen(
                onNavigateToStart = { isAuthenticated, isEmailVerified, userType ->
                    val targetRoute = when {
                        isAuthenticated && isEmailVerified ->
                            if (userType == "brand") Route.BrandGraph else Route.MainGraph
                        isAuthenticated -> Route.AuthGraph.EmailVerification(userType)
                        else            -> Route.AuthGraph
                    }

                    navController.navigate(targetRoute) {
                        popUpTo(Route.Splash) {inclusive = true}
                        launchSingleTop = true
                    }
                }
            )
        }

        authGraph(navController = navController)

        mainGraph(navController = navController)

        brandGraph(navController = navController)
    }
}