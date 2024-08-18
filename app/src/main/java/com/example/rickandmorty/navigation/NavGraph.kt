package com.example.rickandmorty.navigation



import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rickandmorty.screen.CharacterDetailsScreen
import com.example.rickandmorty.screen.CharacterEpisodesScreen
import com.example.rickandmorty.screen.EpisodeScreen
import com.example.rickandmorty.screen.HomeScreen
import com.example.rickandmorty.screen.SearchScreen
import com.example.rickandmorty.screen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = NavRoutes.Splash.route) {
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
        composable(NavRoutes.CharacterDetails.route) { navBackStack->
            val id = navBackStack.arguments!!.getString("id")
            Log.d("userId", "$id")
            if (id != null) {
                Scaffold {
                    CharacterDetailsScreen(navController,id.toInt())
                }
            }
        }
        composable(NavRoutes.CharacterEpisodes.route) { navBackStack->
            val id = navBackStack.arguments!!.getString("id")
            Log.d("userId", "$id")
            if (id != null) {
                Scaffold {
                    CharacterEpisodesScreen(id.toInt())
                }
            }
        }
    }
}

