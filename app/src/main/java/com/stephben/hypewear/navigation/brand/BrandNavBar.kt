package com.stephben.hypewear.navigation.brand

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stephben.hypewear.R
import com.stephben.hypewear.navigation.Route


@Composable
fun BrandNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val bottomNavItems = listOf(
        BrandBottomNavScreens.Home,
        BrandBottomNavScreens.Collection,
        BrandBottomNavScreens.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = modifier
            .height(100.dp)
            .padding(vertical = 14.dp)
            .clip(RoundedCornerShape(100))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        bottomNavItems.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun AddItem(
    screen: BrandBottomNavScreens<out Route>,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any {
       it.route == screen.route::class.qualifiedName
    } == true

    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.surfaceContainerHighest else MaterialTheme.colorScheme.onSurface

    val background =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHighest

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(50.dp)
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = painterResource(screen.icon),
            contentDescription = screen.name,
            tint = contentColor
        )
    }
}


sealed class BrandBottomNavScreens<T>(
    val name: String,
    @DrawableRes val icon: Int,
    val route: T,
) {
    data object Home: BrandBottomNavScreens<Route.BrandGraph.BrandHome>(
        name = "Home",
        icon = R.drawable.home_24,
        route = Route.BrandGraph.BrandHome
    )

    data object Collection: BrandBottomNavScreens<Route.BrandGraph.Collection>(
        name = "Collection",
        icon = R.drawable.checkroom_24,
        route = Route.BrandGraph.Collection
    )

    data object Profile: BrandBottomNavScreens<Route.BrandGraph.Profile>(
        name = "Profile",
        icon = R.drawable.account_circle_24,
        route = Route.BrandGraph.Profile
    )
}