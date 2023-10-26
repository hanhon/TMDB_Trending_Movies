package com.hany.tmdb_movies.trendingmovies.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TrendingMovieEntity::class],
    version = 1
)
abstract class TrendingMoviesDatabase : RoomDatabase() {
    abstract val dao: TrendingMoviesDao
}