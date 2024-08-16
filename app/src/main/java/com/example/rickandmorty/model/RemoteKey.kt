package com.example.rickandmorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val next:Int?,
    val prev:Int?
)