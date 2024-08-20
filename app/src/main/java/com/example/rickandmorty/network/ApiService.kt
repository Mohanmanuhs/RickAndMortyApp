package com.example.rickandmorty.network


import com.example.rickandmorty.model.CharactersPage
import com.example.rickandmorty.model.EpisodesPage
import com.example.rickandmorty.model.RemoteCharacterImage
import com.example.rickandmorty.model.RemoteEpisode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/{ids}")
    suspend fun getCharacterByIds(@Path("ids") ids: String): List<RemoteCharacterImage>

    @GET("character")
    suspend fun searchCharacterByName(
        @Query("page") page: Int,
        @Query("name") name: String
    ): CharactersPage

    @GET("character")
    suspend fun getCharactersListMediator(
        @Query("page") page: Int
    ): CharactersPage

    @GET("episode")
    suspend fun getEpisodesList(
        @Query("page") page: Int
    ): EpisodesPage

    @GET("episode/{ids}")
    suspend fun getCharacterEpisodes(
        @Path("ids") ids:String
    ):List<RemoteEpisode>
}
