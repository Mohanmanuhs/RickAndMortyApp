package com.example.rickandmorty.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rickandmorty.ui.theme.RickTextPrimary

data class DataPoint(
    val title: String,
    val description: String
)

@Composable
fun DataPointComponent(dataPoint: DataPoint) {
    Column{
        Text(
            text = dataPoint.title,
            fontSize = 14.sp,
            color = RickTextPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = dataPoint.description,
            color = RickTextPrimary,
            fontSize = 24.sp
        )
    }
}