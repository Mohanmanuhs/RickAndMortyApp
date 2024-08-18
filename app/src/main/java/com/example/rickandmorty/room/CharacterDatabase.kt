package com.example.rickandmorty.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.model.RemoteCharacter
import com.example.rickandmorty.model.RemoteKey


@Database(entities = [RemoteCharacter::class,RemoteKey::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class CharacterDatabase: RoomDatabase() {
    companion object {
        fun getInstance(context: Context): CharacterDatabase {
            return Room.databaseBuilder(context, CharacterDatabase::class.java, "character_db").build()
        }
    }


    abstract fun getCharacterDao():CharacterDao
    abstract fun getRemoteDao():RemoteKeyDao



}