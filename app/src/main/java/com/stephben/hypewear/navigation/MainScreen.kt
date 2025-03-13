package com.stephben.hypewear.navigation


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stephben.hypewear.apparel.presentation.apparel_detail.components.ApparelDetailHeader
import com.stephben.hypewear.apparel.presentation.apparel_home.components.ApparelHomeHeader

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hideBottomBar = currentRoute.toString().contains("Route.ApparelDetail")


    Log.d("ROUTE", currentRoute.toString())
    Scaffold { padding ->
        Box() {
            MainGraph(
                navController = navController,
                modifier = Modifier
                    .padding(padding)
                    .padding(bottom = 48.dp)
            )
            if (!hideBottomBar) {
                HypeWearBottomNavBar(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 26.dp)
                        .align(Alignment.BottomStart)
                )
            }
        }

    }
}

@Preview
@Composable
private fun MainScreenPrev() {
    MainScreen()
}