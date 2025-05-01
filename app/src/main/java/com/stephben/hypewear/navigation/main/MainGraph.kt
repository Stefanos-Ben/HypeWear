package com.stephben.hypewear.navigation.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.stephben.hypewear.apparel.presentation.apparel_detail.ApparelDetailAction
import com.stephben.hypewear.apparel.presentation.apparel_detail.ApparelDetailScreen
import com.stephben.hypewear.apparel.presentation.apparel_detail.ApparelDetailViewModel
import com.stephben.hypewear.apparel.presentation.home_screen.ApparelListScreen
import com.stephben.hypewear.apparel.presentation.tempadd.AddApparelScreen
import com.stephben.hypewear.apparel.presentation.tempadd.AddApparelViewModel
import com.stephben.hypewear.navigation.Route
import com.stephben.hypewear.user.presentation.favorites.FavoritesScreen
import com.stephben.hypewear.user.presentation.profile.ProfileScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
) {
    navigation<Route.MainGraph>(
        startDestination = Route.MainGraph.HomeScreen
    ) {

        composable<Route.MainGraph.HomeScreen> {
            ApparelListScreen(
                onApparelClick = { apparel ->
                    navController.navigate(
                        Route.MainGraph.ApparelDetail(apparel.apparelID)
                    ) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                bottomBar = {
                    HypeWearBottomNavBar(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 26.dp)
                    )
                },
                modifier = Modifier
            )
        }

        composable<Route.MainGraph.ApparelDetail> { entry ->
            val args = entry.toRoute<Route.MainGraph.ApparelDetail>()
            val apparelDetailViewModel = koinViewModel<ApparelDetailViewModel>()
            apparelDetailViewModel.viewModelScope.launch {
                apparelDetailViewModel.onAction(
                    ApparelDetailAction.OnSelectedApparelChange(args.id)
                )
            }

            ApparelDetailScreen(
                viewModel = apparelDetailViewModel,
                onFavoriteClick = {
                    apparelDetailViewModel.onAction(ApparelDetailAction.OnToggleFavorites)
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onCartClick = {}
            )
        }

        composable<Route.MainGraph.CreateApparelTemp> {
            val addApparelViewModel = koinViewModel<AddApparelViewModel>()
            AddApparelScreen(
                viewModel = addApparelViewModel,
                onAddClick = {
                    navController.navigateUp()
                },
                modifier = Modifier.padding(bottom = 32.dp),
                bottomBar = {
                    HypeWearBottomNavBar(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 26.dp)
                    )
                }
            )
        }

        composable<Route.MainGraph.Profile> {
            ProfileScreen(
                onLogout = {
                    navController.navigate(Route.AuthGraph)
                },
                bottomBar = {
                    HypeWearBottomNavBar(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 26.dp)
                    )
                }
            )
        }

        composable<Route.MainGraph.Favorites> {
            FavoritesScreen(
                onApparelClick = { apparel ->
                    navController.navigate(
                        Route.MainGraph.ApparelDetail(apparel.apparelID)
                    ) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }

                },
                bottomBar = {
                    HypeWearBottomNavBar(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 26.dp)
                    )
                },
                modifier = Modifier
            )
        }

    }
}


