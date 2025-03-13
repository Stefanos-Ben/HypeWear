package com.stephben.hypewear.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RootGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.ApparelGraph
    ){
        composable<Route.ApparelGraph> {
           MainScreen()
        }

        // Details here?
    }
}