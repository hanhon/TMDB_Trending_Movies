package com.hany.tmdb_movies.trendingmovies.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String
)


