package com.example.moviesappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalMovie::class] , version = 2 , exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}