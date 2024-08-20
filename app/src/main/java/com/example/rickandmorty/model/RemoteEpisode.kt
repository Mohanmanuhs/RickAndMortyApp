package com.example.rickandmorty.model

import kotlinx.serialization.Serializable


@Serializable
data class RemoteEpisode(
    val air_date: String,
    val characters: List<String>,
    val episode: String,
    val id: Int,
    val name: String
)

fun RemoteEpisode.toDomainEpisode(): Episode {
    return Episode(id = id,
        name = name,
        seasonNumber = episode.filter { it.isDigit() }.take(2).toInt(),
        episodeNumber = episode.filter { it.isDigit() }.takeLast(2).toInt(),
        airDate = air_date,
        characterIdsInEpisode = characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1).toInt()
        })
}

data class Episode(
    val id: Int,
    val name: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val airDate: String,
    val characterIdsInEpisode: List<Int>
)

