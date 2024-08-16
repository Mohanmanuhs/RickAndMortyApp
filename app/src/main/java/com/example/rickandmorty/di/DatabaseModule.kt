package com.example.rickandmorty.di

import android.content.Context
import com.example.rickandmorty.room.CharacterDao
import com.example.rickandmorty.room.CharacterDatabase
import com.example.rickandmorty.room.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideBeerDatabase(@ApplicationContext context: Context): CharacterDatabase {
        return CharacterDatabase.getInstance(context)
    }


    @Provides
    fun provideCharacterDAO(characterDatabase: CharacterDatabase): CharacterDao = characterDatabase.getCharacterDao()

    @Provides
    fun provideRemoteKeyDao(characterDatabase: CharacterDatabase) :RemoteKeyDao = characterDatabase.getRemoteDao()

}