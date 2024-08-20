package com.example.rickandmorty.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.ui.components.LoadingState

@Composable
fun BottomEpisode(imgList: List<String>, epi: Episode, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Season ${epi.seasonNumber} Episode ${epi.episodeNumber}")
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = epi.airDate, modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
        Text(text = epi.name)
        HorizontalDivider()
        Text(text = "Characters")
        LazyRow(modifier = Modifier
            .fillMaxWidth()
        ) {
            items(imgList) {
                Log.d("MyTag", it)
                SubcomposeAsyncImage(model = it,
                    contentDescription = "Character image",
                    modifier = Modifier
                        .height(220.dp)
                        .aspectRatio(1f)
                        .clip(
                            RoundedCornerShape(12.dp)
                        ),
                    loading = { LoadingState() })
                Spacer(modifier = modifier.width(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

