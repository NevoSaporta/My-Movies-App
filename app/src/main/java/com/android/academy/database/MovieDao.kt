package com.android.academy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.academy.model.MovieModel

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieModel  ORDER BY popularity DESC")
    fun getAll():List<MovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies:List<MovieModel>)

    @Query("DELETE FROM MovieModel")
    fun deleteAll()
}