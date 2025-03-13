package com.stephben.hypewear.navigation

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun HypeWearBottomNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {


    val bottomNavItems = listOf(
        MainBottomNavScreens.Home,
        MainBottomNavScreens.Discover,
        MainBottomNavScreens.Favorites,
        MainBottomNavScreens.Add,
        MainBottomNavScreens.Profile
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
    screen: MainBottomNavScreens<out Route>,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any{
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
            imageVector = screen.icon,
            contentDescription = screen.name,
            tint = contentColor
        )
    }
}


sealed class MainBottomNavScreens<T>(
    val name: String,
    val icon: ImageVector,
    val route: T
) {


    data object Home : MainBottomNavScreens<Route.ApparelList>(
        name = "Home",
        icon = Icons.Outlined.Home,
        route = Route.ApparelList
    )

    data object Discover : MainBottomNavScreens<Route.ApparelSearch>(
        name = "Discover",
        icon = Icons.Rounded.Search,
        route = Route.ApparelSearch(query = "")
    )

    data object Favorites : MainBottomNavScreens<Route.Favorites>(
        name = "Favorites",
        icon = Icons.Rounded.FavoriteBorder,
        route = Route.Favorites
    )

    data object Profile : MainBottomNavScreens<Route.Profile>(
        name = "Profile",
        icon = Icons.Rounded.AccountCircle,
        route = Route.Profile
    )

    data object Add : MainBottomNavScreens<Route.CreateApparelTemp>(
        name = "Add",
        icon = Icons.Outlined.Add,
        route = Route.CreateApparelTemp
    )
}