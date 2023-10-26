package com.hany.tmdb_movies.trendingmovies.data.repository

import com.hany.tmdb_movies.trendingmovies.data.datasource.TrendingMoviesDatabase
import com.hany.tmdb_movies.trendingmovies.data.mappers.toMovie
import com.hany.tmdb_movies.trendingmovies.data.mappers.toMovieDetails
import com.hany.tmdb_movies.trendingmovies.data.mappers.toMovieEntity
import com.hany.tmdb_movies.trendingmovies.data.remote.TrendingTMDBAPI
import com.hany.tmdb_movies.trendingmovies.domain.model.Movie
import com.hany.tmdb_movies.trendingmovies.domain.model.MovieDetails
import com.hany.tmdb_movies.trendingmovies.domain.repo.TrendingMoviesRepository
import com.hany.tmdb_movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingMoviesRepositoryImpl @Inject constructor(
    private val api: TrendingTMDBAPI,
    private val db: TrendingMoviesDatabase
) : TrendingMoviesRepository {
    private val dao = db.dao

    override suspend fun getMovieList(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading<List<Movie>>(true))
            val list = dao.getMovies()
            emit(Resource.Success(
                data = list.map { it.toMovie() }
            ))

            val isDbEmpty = list.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteMovies = try {
                val list = api.getTrendingMovies()
                list
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("No data to load"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("No data to load"))
                null
            }

            remoteMovies?.let { dto ->
                dao.insertMovies(
                    dto.results.map { it.toMovieEntity() }
                )
                emit(Resource.Success(
                    data = dao.getMovies().map { it.toMovie() }
                ))
                emit(Resource.Loading<List<Movie>>(false))
            }

        }
    }

    override suspend fun getMovieDetails(movie: Movie): Flow<Resource<MovieDetails>> =
        flow {
            try {
                emit(Resource.Loading<MovieDetails>(true))
                val response = api.getTrendingMovieDetails(movie.movieID.toString())
                val res = Resource.Success<MovieDetails>(
                    response.toMovieDetails()
                )
                emit(res)
            } catch (e: IOException) {
                e.printStackTrace()
                val res = Resource.Error<MovieDetails>(
                    message = "Couldn't load intraday info" //Todo Localise
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                val res = Resource.Error<MovieDetails>(
                    message = "Couldn't load intraday info" //Todo Localise
                )
                emit(res)
            }
            emit(Resource.Loading<MovieDetails>(false))
        }

}