package com.hany.tmdb_movies.trendingmovies.data.remote

import com.hany.tmdb_movies.trendingmovies.data.remote.dto.MovieDetailsDto
import com.hany.tmdb_movies.trendingmovies.data.remote.dto.TrendingMoviesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TrendingTMDBAPI {

    @GET("discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getTrendingMovies(): TrendingMoviesDto

    @GET("movie/{movie_id}")
    suspend fun getTrendingMovieDetails(
        @Path("movie_id") movieId: String
    ): MovieDetailsDto

}