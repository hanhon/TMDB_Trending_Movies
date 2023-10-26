package com.hany.tmdb_movies.trendingmovies.presentation.moviedetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hany.tmdb_movies.trendingmovies.domain.model.Movie
import com.hany.tmdb_movies.trendingmovies.domain.repo.TrendingMoviesRepository
import com.hany.tmdb_movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: TrendingMoviesRepository
) : ViewModel() {

    private val _state = mutableStateOf(MovieDetailState())
    val detailState: State<MovieDetailState> = _state

    init {
        viewModelScope.launch {
            val movie = savedStateHandle.get<Movie>("movie") ?: return@launch
            getDetails(movie)
        }
    }

    private suspend fun getDetails(movie: Movie) {
        repository.getMovieDetails(movie).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value =  _state.value.copy(isLoading = resource.isLoading)

                }
                is Resource.Success -> {
                    resource.data?.let {
                        _state.value = MovieDetailState(
                            details = it
                        )
                    }
                }
                is Resource.Error -> {
                    _state.value = MovieDetailState(
                        error = true,
                        errorMessage = resource.message ?: "Unknown Message"
                    )
                }
            }
        }
    }

}