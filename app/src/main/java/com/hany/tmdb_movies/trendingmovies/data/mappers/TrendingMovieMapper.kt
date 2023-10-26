package com.hany.tmdb_movies.trendingmovies.data.mappers

import com.hany.tmdb_movies.trendingmovies.data.datasource.TrendingMovieEntity
import com.hany.tmdb_movies.trendingmovies.data.remote.dto.MovieDetailsDto
import com.hany.tmdb_movies.trendingmovies.data.remote.dto.MovieDto
import com.hany.tmdb_movies.trendingmovies.domain.model.Movie
import com.hany.tmdb_movies.trendingmovies.domain.model.MovieDetails

fun TrendingMovieEntity.toMovie(): Movie {
    return Movie(
        name = name,
        imageUrl = imageUrl,
        year = year,
        movieID = movie_id
    )
}

fun MovieDto.toMovieEntity(): TrendingMovieEntity {
    return TrendingMovieEntity(
        name = title,
        imageUrl = posterPath,
        year = releaseDate,
        movie_id = id
    )
}

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        movie = Movie(
            name = title,
            imageUrl = posterPath,
            year = releaseDate,
            movieID = id
        ),
        summary = this.overview
    )
}