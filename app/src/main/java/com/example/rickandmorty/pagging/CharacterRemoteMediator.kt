package com.example.rickandmorty.pagging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.RemoteKey
import com.example.rickandmorty.network.ApiService
import com.example.rickandmorty.room.CharacterDao
import com.example.rickandmorty.room.RemoteKeyDao
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterDao: CharacterDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val apiService: ApiService
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Character>
    ): MediatorResult {
        return try {
            delay(2000)
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getClosestPosition(state)
                    remoteKey?.next?.minus(1) ?: 1
                }
                LoadType.APPEND -> {
                    val remoteKey = getLastPosition(state)
                    val nextPage = remoteKey?.next
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextPage
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
            }
            Log.d("TAG", "initial log loadKey: $loadKey ")
            val remoteResponse =
                apiService.getCharactersListMediator(loadKey)

            if (remoteResponse.isSuccessful) {
                remoteResponse.body()?.let { response ->
                    val list = response.results
                    Log.d("TAG", "load:${loadType} -->  $response")
                    val endOfPagination = list.size < state.config.pageSize

                    if (loadType == LoadType.REFRESH) {
                        characterDao.deleteAllCharacters()
                        remoteKeyDao.deleteAllRemoteKey()
                    }

                    val prev = if (loadKey == 1) null else loadKey - 1
                    val next = if (endOfPagination) null else loadKey + 1

                    list.map {
                        remoteKeyDao.insertRemoteKey(
                            RemoteKey(
                                prev = prev,
                                next = next,
                                id = it.id
                            )
                        )
                    }
                    characterDao.addListOfCharacter(list)
                    MediatorResult.Success(endOfPagination)
                }
                return MediatorResult.Success(endOfPaginationReached = false)
            } else {
                Log.d("TAG", " Error during loading load: ${remoteResponse.errorBody().toString()}")
                MediatorResult.Error(java.lang.Exception(remoteResponse.errorBody().toString()))
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.d("TAG", "load: $e")

            MediatorResult.Error(e)
        }
    }

    private suspend fun getClosestPosition(state: PagingState<Int, Character>): RemoteKey? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let { character ->
                remoteKeyDao.getRemoteKey(character.id)
            }
        }
    }

    private suspend fun getLastPosition(state: PagingState<Int, Character>): RemoteKey? {
        return state.lastItemOrNull()?.let { character ->
            remoteKeyDao.getRemoteKey(character.id)
        }
    }


}