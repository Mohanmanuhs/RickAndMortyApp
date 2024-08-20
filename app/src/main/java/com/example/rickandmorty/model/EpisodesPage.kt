package com.example.rickandmorty.model

import kotlinx.serialization.Serializable

@Serializable
data class EpisodesPage(
    val results: List<RemoteEpisode>
)