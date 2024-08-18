package com.example.rickandmorty.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.model.HomeCharacter
import com.example.rickandmorty.model.toCharacter
import com.example.rickandmorty.model.toDomainEpisode
import com.example.rickandmorty.model.toHomeCharacter
import com.example.rickandmorty.network.ApiService
import com.example.rickandmorty.pagging.CharacterRemoteMediator
import com.example.rickandmorty.room.CharacterDao
import com.example.rickandmorty.room.RemoteKeyDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepo(
    private val apiService: ApiService,
    private val characterDao: CharacterDao,
    private val remoteKeyDao: RemoteKeyDao,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getCharacterStream(): Flow<PagingData<HomeCharacter>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, enablePlaceholders = true
            ), remoteMediator = CharacterRemoteMediator(
                characterDao,remoteKeyDao,apiService
            ),
            pagingSourceFactory = {
                characterDao.getCharacterList()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toHomeCharacter() }
        }
    }

    suspend fun getCharacterById(id: Int):Character?{
        return characterDao.getCharacterBy(id)?.toCharacter()
    }

    suspend fun getCharacterEpisodesList(id: Int):List<Episode>{
        return characterDao.getCharacterEpisodesList(id).map { it.toDomainEpisode() }
    }

}