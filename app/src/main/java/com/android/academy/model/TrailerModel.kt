package com.android.academy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrailerModel (
    @PrimaryKey
    val movieId: Int = 0,
    val id: String ,
    val key: String
)