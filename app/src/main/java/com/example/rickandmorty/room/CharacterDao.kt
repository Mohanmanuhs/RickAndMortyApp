package com.example.rickandmorty.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.model.RemoteCharacter
import com.example.rickandmorty.model.RemoteEpisode


@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListOfCharacter(characters:List<RemoteCharacter>)

    @Query("DELETE FROM RemoteCharacter")
    suspend fun deleteAllCharacters()

    @Query("SELECT * FROM RemoteCharacter")
    fun getCharacterList():PagingSource<Int,RemoteCharacter>

    @Query("SELECT * FROM RemoteCharacter Where id = :id")
    suspend fun getCharacterBy(id:Int):RemoteCharacter?

    @Query("SELECT episode FROM RemoteCharacter Where id = :id")
    suspend fun getCharacterEpisodesList(id:Int):List<RemoteEpisode>




}