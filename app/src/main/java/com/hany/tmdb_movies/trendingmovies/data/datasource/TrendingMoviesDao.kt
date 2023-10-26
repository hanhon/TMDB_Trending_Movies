package com.hany.tmdb_movies.trendingmovies.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(
        trendingMovieEntity: List<TrendingMovieEntity>
    )

    @Query("SELECT * FROM trendingMovieEntity")
    suspend fun getMovies(): List<TrendingMovieEntity>
}