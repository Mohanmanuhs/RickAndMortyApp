package com.example.rickandmorty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EpisodeHeader(header: String) {
    Spacer(modifier = Modifier.height(30.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .border(width = 1.dp, color = Color.Green)
            .padding(10.dp)
    ) {
        Column(modifier = Modifier
            .align(Alignment.TopStart)
            .fillMaxWidth(.8f)) {
            Text(
                text = header, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White
            )
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
}