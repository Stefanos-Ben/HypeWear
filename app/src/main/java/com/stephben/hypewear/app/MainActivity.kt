package com.stephben.hypewear.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.stephben.hypewear.apparel.presentation.apparel_list.ApparelListScreen
import com.stephben.hypewear.apparel.presentation.apparel_list.ApparelListViewModel
import com.stephben.hypewear.apparel.presentation.tempadd.AddApparelScreen
import com.stephben.hypewear.apparel.presentation.tempadd.AddApparelViewModel
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme
import com.stephben.hypewear.navigation.Route
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HypeWearTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.ApparelGraph
                ) {
                    navigation<Route.ApparelGraph>(
                       startDestination =  Route.ApparelList
                    ) {
                        composable<Route.ApparelList> {
                            val apparelListViewModel = koinViewModel<ApparelListViewModel>()
                            ApparelListScreen(
                                viewModel = apparelListViewModel,
                                onApparelClick = {
                                    navController.navigate(
                                        Route.CreateApparelTemp
                                    )
                                },
                                modifier = Modifier
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
        }
    }
}
