package com.example.rickandmorty.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.model.Character


@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListOfCharacter(characters:List<Character>)

    @Query("DELETE FROM Character")
    suspend fun deleteAllCharacters()

    @Query("SELECT * FROM Character")
    fun getCharacterList():PagingSource<Int,Character>




}