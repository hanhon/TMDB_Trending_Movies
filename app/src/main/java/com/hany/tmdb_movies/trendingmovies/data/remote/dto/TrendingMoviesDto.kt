package com.hany.tmdb_movies.trendingmovies.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TrendingMoviesDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)