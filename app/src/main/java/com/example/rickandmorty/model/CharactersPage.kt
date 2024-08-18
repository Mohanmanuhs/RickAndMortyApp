package com.example.rickandmorty.model

import kotlinx.serialization.Serializable

@Serializable
data class CharactersPage(
    val results: List<RemoteCharacter>
)