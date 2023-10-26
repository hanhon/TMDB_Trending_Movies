package com.hany.tmdb_movies.trendingmovies.presentation.movieslist

import com.hany.tmdb_movies.trendingmovies.domain.model.Movie

data class MovieListState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String = "",
    val error: Boolean = false
)
