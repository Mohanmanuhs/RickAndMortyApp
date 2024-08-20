package com.example.rickandmorty.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.model.HomeCharacter
import com.example.rickandmorty.model.toHomeCharacter
import com.example.rickandmorty.network.ApiService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class SearchCharacterPagingSource(private val apiService: ApiService, private val name: String): PagingSource<Int, HomeCharacter>() {
    override fun getRefreshKey(state: PagingState<Int, HomeCharacter>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeCharacter> {
        val position = params.key?:1
        return try {
            delay(1000)
            val remoteData = apiService.searchCharacterByName(position,name)
            val episodes = remoteData.results.map {
                it.toHomeCharacter()
            }

            val nextKey = if(episodes.size<params.loadSize){
                null
            }else{
                position+1
            }
            LoadResult.Page(
                data = episodes,
                prevKey = if(position==1) null else position-1,
                nextKey = nextKey
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
}