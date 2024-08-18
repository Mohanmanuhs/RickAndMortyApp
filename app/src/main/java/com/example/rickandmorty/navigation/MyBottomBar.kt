package com.example.rickandmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rickandmorty.model.NavBarItem

@Composable
fun MyBottomBar(navController1: NavHostController) {
    val backStackEntry = navController1.currentBackStackEntryAsState()
    val listOfNavBarItems = listOf(
        NavBarItem(
            "Home", NavRoutes.Home.route, Icons.Rounded.Home
        ),
        NavBarItem(
            "Episodes", NavRoutes.Episodes.route, Icons.Rounded.PlayArrow
        ),
        NavBarItem(
            "Search", NavRoutes.Search.route, Icons.Rounded.Search
        ) 
     )
    BottomAppBar {
        listOfNavBarItems.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(selected = selected,
                onClick = { navController1.navigate(it.route){
                    popUpTo(navController1.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop=true
                } },
                icon = { Icon(imageVector = it.icon, contentDescription = it.title) })
        }
    }
}

