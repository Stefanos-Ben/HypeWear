package com.stephben.hypewear.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import com.stephben.hypewear.navigation.auth.authGraph
import com.stephben.hypewear.navigation.brand.brandGraph
import com.stephben.hypewear.navigation.main.mainGraph

@Composable
fun RootGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.AuthGraph
    ){

        authGraph(navController = navController)

        mainGraph(navController = navController)

        brandGraph(navController = navController)
    }
}