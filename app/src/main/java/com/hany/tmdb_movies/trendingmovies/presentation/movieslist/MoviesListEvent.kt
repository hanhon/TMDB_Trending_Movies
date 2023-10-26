package com.hany.tmdb_movies.trendingmovies.presentation.movieslist

sealed class MoviesListEvent {

    object Refresh : MoviesListEvent()
}
