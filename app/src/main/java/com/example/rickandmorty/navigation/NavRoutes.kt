package com.example.rickandmorty.navigation

sealed class NavRoutes(val route:String){

    data object Home:NavRoutes("Home")
    data object Episodes:NavRoutes("Episodes")
    data object CharacterEpisodes:NavRoutes("CharacterEpisodes/{id}")
    data object Splash:NavRoutes("Splash")

    data object BottomNav:NavRoutes("BottomNav")

    data object Search:NavRoutes("Search")
    data object CharacterDetails:NavRoutes("CharacterDetails/{id}")

}
