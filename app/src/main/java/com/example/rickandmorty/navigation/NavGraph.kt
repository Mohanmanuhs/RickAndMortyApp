package com.example.rickandmorty.navigation


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickandmorty.screen.CharacterDetailsScreen
import com.example.rickandmorty.screen.CharacterEpisodesScreen
import com.example.rickandmorty.screen.EpisodeScreen
import com.example.rickandmorty.screen.HomeScreen
import com.example.rickandmorty.screen.SearchScreen
import com.example.rickandmorty.screen.SplashScreen
import com.example.rickandmorty.ui.theme.RickPrimary

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        modifier = Modifier.background(color = RickPrimary),
        navController = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        composable(NavRoutes.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavRoutes.BottomNav.route) {
            BottomNavScreen(navController)
        }
        composable(NavRoutes.Home.route) {
            HomeScreen(navController)
        }
        composable(NavRoutes.Episodes.route) {
            EpisodeScreen(navController)
        }
        composable(NavRoutes.Search.route) {
            SearchScreen(navController)
        }
        composable(NavRoutes.CharacterDetails.route, arguments = listOf(navArgument("id") {
            type = NavType.IntType
        })) { navBackStack ->
            val id = navBackStack.arguments!!.getInt("id")
            Log.d("userId", "$id")
            CharacterDetailsScreen(navController, id)
        }
        composable(NavRoutes.CharacterEpisodes.route, arguments = listOf(navArgument("id") {
            type = NavType.IntType
        })) { navBackStack ->
            val id = navBackStack.arguments!!.getInt("id")
            Log.d("userId", "$id")
            CharacterEpisodesScreen(navController,{navController.navigateUp()},id)
        }
    }
}

