package com.example.rickandmorty.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.ui.components.CharacterView
import com.example.rickandmorty.viewmodels.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    var search by remember { mutableStateOf("") }
    val pagingData = searchViewModel.searchByCharacter(search).collectAsLazyPagingItems()


    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(value = search,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            onValueChange = { search = it },
            label = { Text("Search Character") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") })
        if (search.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Search Characters...")
            }
        } else {
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
                                    navController, character
                                )
                            }
                        }
                    }
                }
        }
    }
}