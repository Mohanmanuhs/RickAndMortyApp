package com.example.rickandmorty.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.ui.components.CharacterView
import com.example.rickandmorty.viewmodels.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier, homeViewModel: HomeViewModel= hiltViewModel()) {

    val pagingData = homeViewModel.pagingData.collectAsLazyPagingItems()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)


    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        pagingData.refresh()
        swipeRefreshState.isRefreshing = true
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                pagingData.loadState.mediator?.refresh is LoadState.Loading->{
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                pagingData.loadState.mediator?.refresh is LoadState.NotLoading && pagingData.itemCount > 0 -> {
                    swipeRefreshState.isRefreshing = false
                    LazyVerticalGrid(
                        modifier = modifier,
                        contentPadding = PaddingValues(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        columns = GridCells.Fixed(2),
                    ) {
                        items(pagingData.itemCount, key = { index ->
                            pagingData[index]?.id ?: index
                        }) {
                            it.let {
                                pagingData[it]?.let { character ->
                                    CharacterView(
                                        navController,
                                        character
                                    )
                                }
                            }
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}