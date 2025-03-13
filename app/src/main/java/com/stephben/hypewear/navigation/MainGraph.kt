package com.stephben.hypewear.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.stephben.hypewear.apparel.presentation.apparel_detail.ApparelDetailAction
import com.stephben.hypewear.apparel.presentation.apparel_detail.ApparelDetailScreen
import com.stephben.hypewear.apparel.presentation.apparel_detail.ApparelDetailViewModel
import com.stephben.hypewear.apparel.presentation.apparel_home.ApparelListScreen
import com.stephben.hypewear.apparel.presentation.apparel_home.ApparelListViewModel
import com.stephben.hypewear.apparel.presentation.tempadd.AddApparelScreen
import com.stephben.hypewear.apparel.presentation.tempadd.AddApparelViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Route.ApparelGraph
    )
    {
        navigation<Route.ApparelGraph>(
            startDestination = Route.ApparelList
        ) {

            composable<Route.ApparelList> {
                val apparelListViewModel = koinViewModel<ApparelListViewModel>()
                ApparelListScreen(
                    viewModel = apparelListViewModel,
                    onApparelClick = { apparel ->
                        navController.navigate(
                            Route.ApparelDetail(apparel.apparelID)
                        )
                    },
                    modifier = modifier
                )
            }

            composable<Route.ApparelDetail> { entry ->
                val args = entry.toRoute<Route.ApparelDetail>()
                val apparelDetailViewModel = koinViewModel<ApparelDetailViewModel>()
                apparelDetailViewModel.viewModelScope.launch {
                    apparelDetailViewModel.onAction(
                        ApparelDetailAction.OnSelectedApparelChange(args.id)
                    )
                }
                
                ApparelDetailScreen(
                    viewModel = apparelDetailViewModel,
                    onFavoriteClick = {},
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCartClick = {}
                )
            }

            composable<Route.CreateApparelTemp> {
                val addApparelViewModel = koinViewModel<AddApparelViewModel>()
                AddApparelScreen(
                    viewModel = addApparelViewModel,
                    onAddClick = {
                        navController.navigate(Route.ApparelList)
                    }
                )
            }

        }
    }
}