package com.hany.tmdb_movies.trendingmovies.data.datasource

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["movie_id"], unique = true)])
data class TrendingMovieEntity(
    val name: String,
    val imageUrl: String,
    val year: String,
    val movie_id: Int,
    @PrimaryKey val id: Int? = null
)
