package com.android.academy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.academy.model.MovieModel
import com.android.academy.model.TrailerModel

@Database(entities = [MovieModel::class,TrailerModel::class],version = 2)
abstract class AppDatabase:RoomDatabase() {

    abstract fun movieDao(): MovieDao?
    abstract fun trailerDao(): TrailerDao?

    companion object{

        private const val DATABASE_NAME = "movies"

        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }

    }
}