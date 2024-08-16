package com.example.rickandmorty.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.network.ApiService
import com.example.rickandmorty.pagging.CharacterRemoteMediator
import com.example.rickandmorty.room.CharacterDao
import com.example.rickandmorty.room.RemoteKeyDao
import kotlinx.coroutines.flow.Flow

class CharacterRepo(
    private val apiService: ApiService,
    private val characterDao: CharacterDao,
    private val remoteKeyDao: RemoteKeyDao,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getCharacterStream(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ), remoteMediator = CharacterRemoteMediator(
                characterDao,remoteKeyDao,apiService
            ),
            pagingSourceFactory = {
                characterDao.getCharacterList()
            }
        ).flow
    }

}