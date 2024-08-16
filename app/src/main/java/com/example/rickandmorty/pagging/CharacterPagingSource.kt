package com.example.rickandmorty.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.network.ApiService
import okio.IOException

class CharacterPagingSource(private val apiService: ApiService): PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key?:1
        return try {
            val remoteData = apiService.getCharactersList(position)
            val characters = remoteData.results
            val nextKey = if(characters.size<params.loadSize){
                null
            }else{
                position+1
            }
            LoadResult.Page(
                data = characters,
                prevKey = if(position==1) null else position-1,
                nextKey = nextKey
            )
        }catch (e:IOException){
            LoadResult.Error(e)
        }catch (e:HttpException){
            LoadResult.Error(e)
        }
    }
}