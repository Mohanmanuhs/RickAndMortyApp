package com.example.rickandmorty.model

sealed class EpisodesUiModel {
    class Item(val episode: Episode): EpisodesUiModel()
    class Header(val text: String): EpisodesUiModel()
}