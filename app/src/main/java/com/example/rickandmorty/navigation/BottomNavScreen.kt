package com.example.rickandmorty.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.screen.EpisodeScreen
import com.example.rickandmorty.screen.HomeScreen
import com.example.rickandmorty.screen.SearchScreen
import com.example.rickandmorty.ui.theme.RickAction
import com.example.rickandmorty.ui.theme.RickPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val navController1 = rememberNavController()

    Scaffold(bottomBar = {
        MyBottomBar(navController1)
    }, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = RickPrimary), title = {
            Text(text = "Rick And Morty", color = RickAction)
        })
    }) { innerPadding ->
        NavHost(
            navController = navController1,
            modifier = Modifier
                .background(RickPrimary)
                .padding(innerPadding),
            startDestination = NavRoutes.Home.route
        ) {
            composable(NavRoutes.Home.route) {
                HomeScreen(navHostController)
            }
            composable(NavRoutes.Search.route) {
                SearchScreen(navController1)
            }
            composable(NavRoutes.Episodes.route) {
                EpisodeScreen(navController1)
            }
        }
    }
}

