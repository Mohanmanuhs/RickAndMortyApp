package com.example.rickandmorty.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rickandmorty.navigation.NavRoutes
import com.example.rickandmorty.ui.theme.RickTextPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, modifier: Modifier = Modifier) {

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        navController.navigate(NavRoutes.BottomNav.route)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        Text(text = "Rick And Morty App",color= RickTextPrimary, fontWeight = FontWeight.Bold, fontSize = 25.sp)
    }
}