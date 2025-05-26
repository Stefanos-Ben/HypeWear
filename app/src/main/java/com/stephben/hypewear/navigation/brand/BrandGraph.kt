package com.stephben.hypewear.navigation.brand

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormScreen
import com.stephben.hypewear.brand.presentation.brand_home.BrandHomeScreen
import com.stephben.hypewear.brand.presentation.collection.CollectionScreen
import com.stephben.hypewear.brand.presentation.profile.BrandProfileScreen
import com.stephben.hypewear.navigation.Route

fun NavGraphBuilder.brandGraph(
    navController: NavHostController,
) {
    navigation<Route.BrandGraph>(
        startDestination = Route.BrandGraph.BrandHome
    ){
        composable<Route.BrandGraph.BrandHome> {
            BrandHomeScreen(
                bottomBar = {
                    BrandNavBar(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 26.dp)
                    )
                }
            )
        }

        composable<Route.BrandGraph.Collection> {
            CollectionScreen(
                bottomBar = {
                    BrandNavBar(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 26.dp)
                    )
                },
                onAddApparel = {
                    navController.navigate(Route.BrandGraph.AddApparel()) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onEditClick = { apparelId ->
                    navController.navigate(Route.BrandGraph.AddApparel(apparelId)){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Route.BrandGraph.Profile> {
            BrandProfileScreen(
                onLogout = {
                    navController.navigate(Route.AuthGraph)
                },
                bottomBar = {
                    BrandNavBar(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 26.dp)
                    )
                }
            )
        }

        composable<Route.BrandGraph.AddApparel> { entry ->
            val args = entry.toRoute<Route.BrandGraph.AddApparel>()
            ApparelFormScreen(
                apparelId = args.id,
                onLeave = {
                    navController.navigate(Route.BrandGraph.Collection) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}