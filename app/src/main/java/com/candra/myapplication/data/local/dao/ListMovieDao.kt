package com.candra.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.candra.myapplication.data.model.ListMovieModel

@Dao
interface ListMovieDao {
    @Insert
    suspend fun insertAll(listUser: List<ListMovieModel>)

    @Query("SELECT * FROM listmoviemodel")
    suspend fun getListMovie() : List<ListMovieModel>

    @Query("DELETE FROM listmoviemodel")
    suspend fun deleteAll()
}