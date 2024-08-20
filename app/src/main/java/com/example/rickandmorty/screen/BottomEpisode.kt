package com.example.rickandmorty.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.model.RemoteCharacterImage
import com.example.rickandmorty.navigation.NavRoutes
import com.example.rickandmorty.ui.components.LoadingState
import com.example.rickandmorty.ui.theme.Bg
import com.example.rickandmorty.ui.theme.RickTextPrimary

@Composable
fun BottomEpisode(
    navController: NavHostController,
    imgList: List<RemoteCharacterImage>,
    epi: Episode,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
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
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(imgList) {
                Box(modifier = Modifier.width(220.dp)
                    .clickable {
                        val routes = NavRoutes.CharacterDetails.route.replace("{id}", "${it.id}")
                        Log.d("MohanTag",it.id.toString())
                        navController.navigate(routes)
                    }
                     // Constraint the Box to the width of the image
                ) {
                    SubcomposeAsyncImage(model = it.image,
                        contentDescription = "Character image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp)),
                        loading = { LoadingState() })
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(Bg)
                            .padding(8.dp)
                            .padding(start = 5.dp),
                        text = it.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = RickTextPrimary
                    )
                }
                Spacer(modifier = modifier.width(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

