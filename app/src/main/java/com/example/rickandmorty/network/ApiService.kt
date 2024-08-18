package com.example.rickandmorty.network


import com.example.rickandmorty.model.CharactersPage
import com.example.rickandmorty.model.RemoteCharacter
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): RemoteCharacter

    @GET("character")
    suspend fun getCharactersListMediator(
        @Query("page") page: Int
    ): CharactersPage
}
