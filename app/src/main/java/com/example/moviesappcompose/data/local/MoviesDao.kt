package com.example.moviesappcompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {
    @Insert(entity = LocalMovie::class , onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: LocalMovie)

    @Query("SELECT * FROM movies")
    suspend fun getFavoriteMovies(): List<LocalMovie>

    @Delete
    suspend fun deleteMovie(movie: LocalMovie)
}