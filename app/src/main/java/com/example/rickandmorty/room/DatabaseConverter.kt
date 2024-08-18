package com.example.rickandmorty.room

import androidx.room.TypeConverter
import com.example.rickandmorty.model.RemoteCharacter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatabaseConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromLocation(location: RemoteCharacter.Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun toLocation(locationString: String): RemoteCharacter.Location {
        return gson.fromJson(locationString, RemoteCharacter.Location::class.java)
    }

    @TypeConverter
    fun fromOrigin(origin: RemoteCharacter.Origin): String {
        return gson.toJson(origin)
    }

    @TypeConverter
    fun toOrigin(originString: String): RemoteCharacter.Origin {
        return gson.fromJson(originString, RemoteCharacter.Origin::class.java)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(listString: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(listString, listType)
    }
}

