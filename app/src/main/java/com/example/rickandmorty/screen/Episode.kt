package com.example.rickandmorty.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.model.EpisodesUiModel
import com.example.rickandmorty.ui.components.EpisodeHeader
import com.example.rickandmorty.ui.components.EpisodeRow
import com.example.rickandmorty.ui.components.LoadingState
import com.example.rickandmorty.viewmodels.EpisodeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    episodeViewModel: EpisodeViewModel = hiltViewModel()
) {
    val pagingData = episodeViewModel.pagingData.collectAsLazyPagingItems()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val imgList by episodeViewModel.imgList.collectAsState()
    var epi by remember {
        mutableStateOf<Episode?>(null)
    }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        pagingData.refresh()
        swipeRefreshState.isRefreshing = true
    }) {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                }, sheetState = sheetState
            ) {
                // Sheet content
                epi?.let { BottomEpisode(navController,imgList = imgList, epi = it) }
            }
        }
        when (pagingData.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingState()
            }

            is LoadState.NotLoading -> {
                swipeRefreshState.isRefreshing = false
                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(pagingData.itemCount) {
                        it.let {
                            pagingData[it]?.let { episodeUiModel ->
                                when (episodeUiModel) {
                                    is EpisodesUiModel.Item -> {
                                        EpisodeRow({
                                            epi = episodeUiModel.episode
                                            episodeViewModel.getCharacterImgList(episodeUiModel.episode.characterIdsInEpisode)
                                            showBottomSheet = !showBottomSheet
                                        }, episode = episodeUiModel.episode)
                                    }

                                    is EpisodesUiModel.Header -> {
                                        EpisodeHeader(episodeUiModel.text)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            else -> {
                LoadingState()
            }
        }
    }
}
