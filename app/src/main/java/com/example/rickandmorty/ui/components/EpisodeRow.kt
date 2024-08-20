package com.example.rickandmorty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.ui.theme.PurpleGrey40
import com.example.rickandmorty.ui.theme.RickAction
import com.example.rickandmorty.ui.theme.RickTextPrimary

@Composable
fun EpisodeRow(onClick: () -> Unit, episode: Episode) {
    Box(modifier = Modifier
        .clickable {
            onClick()
        }
        .fillMaxWidth()
        .border(width = 1.dp, color = RickAction)
        .padding(top = 0.dp, start = 10.dp, bottom = 10.dp)) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(.8f)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = episode.name, fontSize = 20.sp, color = RickTextPrimary
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = episode.airDate, color = RickTextPrimary, fontStyle = FontStyle.Italic
            )
        }
        Text(
            modifier = Modifier
                .background(PurpleGrey40)
                .align(Alignment.TopEnd)
                .padding(5.dp),
            text = "S.${episode.seasonNumber} E.${episode.episodeNumber}",
            fontSize = 16.sp,
            color = RickTextPrimary,
            fontStyle = FontStyle.Italic
        )
    }
}