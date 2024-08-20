package com.example.rickandmorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.model.RemoteCharacter.Location
import com.example.rickandmorty.model.RemoteCharacter.Origin
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class RemoteCharacter(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
){
    @Serializable
    data class Location(
        val name: String,
        val url: String
    )
    @Serializable
    data class Origin(
        val name: String,
        val url: String
    )
}

@Serializable
data class RemoteCharacterImage(
    val id:Int,
    val image:String,
    val name:String
)

fun RemoteCharacter.toCharacter():Character{
    val characterGender = when (gender.lowercase()) {
        "female" -> CharacterGender.Female
        "male" -> CharacterGender.Male
        "genderless" -> CharacterGender.Genderless
        else -> CharacterGender.Unknown
    }
    val characterStatus = when (status.lowercase()) {
        "alive" -> CharacterStatus.Alive
        "dead" -> CharacterStatus.Dead
        else -> CharacterStatus.Unknown
    }
    return Character(
        created = created,
        episode = episode.map { it.substring(it.lastIndexOf("/") + 1).toInt() },
        gender = characterGender,
        id = id,
        image = image,
        location =location,
        name = name,
        origin = origin,
        species = species,
        status = characterStatus,
        type = type,
        url = url
    )
}

fun RemoteCharacter.toHomeCharacter():HomeCharacter{
    val characterStatus = when (status.lowercase()) {
        "alive" -> CharacterStatus.Alive
        "dead" -> CharacterStatus.Dead
        else -> CharacterStatus.Unknown
    }
    return HomeCharacter(
        id = id,
        image = image,
        name = name,
        status = characterStatus
    )
}

data class HomeCharacter(
    val id: Int,
    val image: String,
    val name: String,
    val status: CharacterStatus
)

data class Character(
    val id: Int,
    val created: String,
    val episode: List<Int>,
    val gender: CharacterGender,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: CharacterStatus,
    val type: String,
    val url: String
)