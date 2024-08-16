package com.example.rickandmorty.di

import com.example.rickandmorty.network.ApiService
import com.example.rickandmorty.repository.CharacterRepo
import com.example.rickandmorty.room.CharacterDao
import com.example.rickandmorty.room.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://rickandmortyapi.com/api/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesApiService(): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideCharacterRepo(
        apiService: ApiService,
        characterDao: CharacterDao,
        remoteKeyDao: RemoteKeyDao
    ): CharacterRepo {
        return CharacterRepo(apiService, characterDao,remoteKeyDao)
    }

}