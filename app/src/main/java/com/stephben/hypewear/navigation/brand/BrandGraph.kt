package com.stephben.hypewear.navigation.brand

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stephben.hypewear.brand.presentation.brand_home.BrandHomeScreen
import com.stephben.hypewear.navigation.Route

fun NavGraphBuilder.brandGraph(
    navController: NavHostController,
) {
    navigation<Route.BrandGraph>(
        startDestination = Route.BrandGraph.BrandHome
    ){
        composable<Route.BrandGraph.BrandHome> {
            BrandHomeScreen()
        }
    }
}