package com.example.rickandmorty.network

import com.example.rickandmorty.model.CharactersPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharactersList(
        @Query("page") page: Int
    ): CharactersPage

    @GET("character")
    suspend fun getCharactersListMediator(
        @Query("page") page: Int
    ): Response<CharactersPage>

}
