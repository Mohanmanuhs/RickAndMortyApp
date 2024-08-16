package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        Text(text = "Rick And Morty")

                    })
                }) { innerPadding ->
                    Paging(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Paging(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {

    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        pagingData.refresh()
        swipeRefreshState.isRefreshing = true
    }) {
        LazyColumn {

            if (pagingData.loadState.refresh is LoadState.Loading) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            if (pagingData.loadState.refresh is LoadState.NotLoading||pagingData.itemCount>0) {
                swipeRefreshState.isRefreshing = false
                items(pagingData.itemCount) {
                    it.let {
                        pagingData[it]?.let { it1 -> CharacterView(it1) }
                    }
                }
            }
            if (pagingData.loadState.refresh is LoadState.Error && pagingData.itemCount<1) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize()) {
                        Text(text = "Error Occurred",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clickable {
                                    pagingData.refresh()
                                })
                    }
                }
            }

            if (pagingData.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            if (pagingData.loadState.append is LoadState.Error) {
                item {
                    ErrorFooter {
                        pagingData.retry()
                    }
                }
            }

            if (pagingData.loadState.prepend is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            if (pagingData.loadState.prepend is LoadState.Error) {
                item {
                    ErrorHeader {
                        pagingData.retry()
                    }
                }
            }

        }
    }
}

@Composable
fun ErrorHeader(retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(text = "Tap to Retry",
            modifier = Modifier
                .clickable { retry.invoke() }
                .align(Alignment.Center))
    }
}


@Preview(showBackground = true)
@Composable
fun ErrorFooter(retry: () -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(imageVector = Icons.Default.Warning, contentDescription = null)
            Text(
                text = "Error Occurred", modifier = Modifier.padding(horizontal = 8.dp)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Retry",
                    modifier = Modifier
                        .clickable { retry.invoke() }
                        .align(Alignment.CenterEnd))
            }
        }
    }

}

@Composable
fun CharacterView(character: Character, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(10.dp)) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            painter = rememberAsyncImagePainter(model = character.image),
            contentDescription = "img"
        )

        Text(text = character.name)

    }
}