package com.example.rickandmorty.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rickandmorty.ui.theme.RickTextPrimary

@Composable
fun CharacterNameComponent(name: String) {
    Text(
        text = name,
        fontSize = 42.sp,
        lineHeight = 42.sp,
        color = RickTextPrimary,
        fontWeight = FontWeight.Bold
    )
}