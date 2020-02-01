package com.android.academy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.academy.model.TrailerModel


@Dao
interface TrailerDao {
    @Query("SELECT * FROM TrailerModel WHERE movieId = :id")
    fun getVideo(id: Int): TrailerModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(videoModel: TrailerModel?)
}
