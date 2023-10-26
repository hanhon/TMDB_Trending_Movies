package com.hany.tmdb_movies.trendingmovies.domain.repo

import com.hany.tmdb_movies.trendingmovies.domain.model.Movie
import com.hany.tmdb_movies.trendingmovies.domain.model.MovieDetails
import com.hany.tmdb_movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TrendingMoviesRepository {
    suspend fun getMovieList(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovieDetails(movie: Movie): Flow<Resource<MovieDetails>>
}