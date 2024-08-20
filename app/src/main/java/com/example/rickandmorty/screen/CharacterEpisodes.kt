package com.example.rickandmorty.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.ui.components.CharacterImage
import com.example.rickandmorty.ui.components.CharacterNameComponent
import com.example.rickandmorty.ui.components.DataPoint
import com.example.rickandmorty.ui.components.DataPointComponent
import com.example.rickandmorty.ui.components.EpisodeRowComponent
import com.example.rickandmorty.ui.components.LoadingState
import com.example.rickandmorty.ui.components.SimpleToolbar
import com.example.rickandmorty.ui.theme.RickPrimary
import com.example.rickandmorty.ui.theme.RickTextPrimary
import com.example.rickandmorty.viewmodels.CharacterEpisodesViewModel
import com.example.rickandmorty.viewmodels.EpisodeViewModel

@Composable
fun CharacterEpisodesScreen(onBackClicked: () -> Unit,characterId:Int, modifier: Modifier = Modifier,characterEpisodesViewModel: CharacterEpisodesViewModel = hiltViewModel(),episodesViewModel: EpisodeViewModel= hiltViewModel()) {

    val list = characterEpisodesViewModel.episodeList.collectAsState().value
    val character by characterEpisodesViewModel.character.collectAsState()


    LaunchedEffect(Unit) {
        characterEpisodesViewModel.getCharacterEpisodesList(characterId)
    }

    if(character != null && list.isNotEmpty()){
        MainScreen(episodesViewModel,character = character!!, episodes = list) { onBackClicked()}
    }else {
        LoadingState()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(episodesViewModel: EpisodeViewModel,character: Character, episodes: List<Episode>, onBackClicked: () -> Unit) {
    val episodeBySeasonMap = episodes.groupBy { it.seasonNumber }
    val imgList by episodesViewModel.imgList.collectAsState()
    var epi by remember {
        mutableStateOf<Episode?>(null)
    }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            }, sheetState = sheetState
        ) {
            // Sheet content
            epi?.let { BottomEpisode(imgList = imgList, epi = it) }
        }
    }
    Column {
        SimpleToolbar(title = "Character episodes", onBackAction = onBackClicked)
        LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
            item { CharacterNameComponent(name = character.name) }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                LazyRow {
                    episodeBySeasonMap.forEach { mapEntry ->
                        val title = "Season ${mapEntry.key}"
                        val description = "${mapEntry.value.size} ep"
                        item {
                            DataPointComponent(dataPoint = DataPoint(title, description))
                            Spacer(modifier = Modifier.width(32.dp))
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CharacterImage(imageUrl = character.image) }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            episodeBySeasonMap.forEach { mapEntry ->
                stickyHeader { SeasonHeader(seasonNumber = mapEntry.key) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(mapEntry.value) { episode ->
                    EpisodeRowComponent(
                        {
                            epi = episode
                            episodesViewModel.getCharacterImgList(episode.characterIdsInEpisode)
                            showBottomSheet = !showBottomSheet
                        },
                        episode = episode)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = RickPrimary)
            .padding(top = 8.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Season $seasonNumber",
            color = RickTextPrimary,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = RickTextPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp)
        )
    }
}