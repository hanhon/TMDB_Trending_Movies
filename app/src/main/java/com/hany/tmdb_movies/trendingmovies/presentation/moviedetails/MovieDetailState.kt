package com.hany.tmdb_movies.trendingmovies.presentation.moviedetails

import com.hany.tmdb_movies.trendingmovies.domain.model.MovieDetails

data class MovieDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val error: Boolean = false,
    val details: MovieDetails? = null
)
