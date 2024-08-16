package com.example.rickandmorty.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.model.RemoteKey


@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKey: RemoteKey)

    @Query("DELETE FROM RemoteKey")
    suspend fun deleteAllRemoteKey()

    @Query("SELECT * FROM RemoteKey WHERE id==:id")
    suspend fun getRemoteKey(id: Int):RemoteKey




}