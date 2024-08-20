package com.example.rickandmorty.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.model.EpisodesUiModel
import com.example.rickandmorty.model.HomeCharacter
import com.example.rickandmorty.model.RemoteCharacterImage
import com.example.rickandmorty.model.toCharacter
import com.example.rickandmorty.model.toDomainEpisode
import com.example.rickandmorty.model.toHomeCharacter
import com.example.rickandmorty.network.ApiService
import com.example.rickandmorty.pagging.CharacterRemoteMediator
import com.example.rickandmorty.pagging.EpisodePagingSource
import com.example.rickandmorty.pagging.SearchCharacterPagingSource
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
        return Pager(config = PagingConfig(
            pageSize = 8
        ), remoteMediator = CharacterRemoteMediator(
            characterDao, remoteKeyDao, apiService
        ), pagingSourceFactory = {
            characterDao.getCharacterList()
        }).flow.map { pagingData ->
            pagingData.map { it.toHomeCharacter() }
        }
    }

    fun getSearchCharacterStream(name:String): Flow<PagingData<HomeCharacter>> {
        return Pager(PagingConfig(
            pageSize = 5
        ),pagingSourceFactory = {
            SearchCharacterPagingSource(apiService,name)
        }).flow
    }

    suspend fun getCharacterById(id: Int): Character? {
        return characterDao.getCharacterBy(id)?.toCharacter()
    }

    suspend fun getCharactersImageList(list:List<Int>):List<RemoteCharacterImage> {
        return apiService.getCharacterByIds(list.joinToString(separator = ","))
    }

    suspend fun getCharacterEpisodesList(id: Int): List<Episode> {
        val character = characterDao.getCharacterBy(id)?.toCharacter()
        val episodes = character?.episode ?: emptyList()

        if (episodes.size == 1) {
            return apiService.getCharacterEpisodes("${episodes[0]},").map { it.toDomainEpisode() }
        } else {
            val epi = character?.episode?.joinToString(",") ?: ""
            return apiService.getCharacterEpisodes(epi).map { it.toDomainEpisode() }
        }
    }

    fun getEpisodesStream(): Flow<PagingData<EpisodesUiModel>> {
        return Pager(config = PagingConfig(
            pageSize = 3,
        ), pagingSourceFactory = {
            EpisodePagingSource(apiService)
        }).flow.map {
            it.insertSeparators { episodesUiModel: EpisodesUiModel?, episodesUiModel2: EpisodesUiModel? ->
                if (episodesUiModel == null) {
                    return@insertSeparators EpisodesUiModel.Header("Season 1")
                }

                // No footer
                if (episodesUiModel2 == null) {
                    return@insertSeparators null
                }

                // Make sure we only care about the items (episodes)
                if (episodesUiModel is EpisodesUiModel.Header || episodesUiModel2 is EpisodesUiModel.Header) {
                    return@insertSeparators null
                }

                // Little logic to determine if a separator is necessary
                val episode1 = (episodesUiModel as EpisodesUiModel.Item).episode
                val episode2 = (episodesUiModel2 as EpisodesUiModel.Item).episode
                return@insertSeparators if (episode2.seasonNumber != episode1.seasonNumber) {
                    EpisodesUiModel.Header("Season ${episode2.seasonNumber}")
                } else {
                    null
                }
            }
        }
    }
}