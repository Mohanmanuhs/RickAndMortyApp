package com.example.rickandmorty.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.model.EpisodesUiModel
import com.example.rickandmorty.model.toDomainEpisode
import com.example.rickandmorty.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class EpisodePagingSource(private val apiService: ApiService): PagingSource<Int, EpisodesUiModel>() {
    override fun getRefreshKey(state: PagingState<Int, EpisodesUiModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesUiModel> {
        val position = params.key?:1
        return try {
            val remoteData = apiService.getEpisodesList(position)
            val episodes = remoteData.results.map {
                EpisodesUiModel.Item(it.toDomainEpisode())
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