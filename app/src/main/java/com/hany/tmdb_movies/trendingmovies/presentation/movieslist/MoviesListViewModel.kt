package com.hany.tmdb_movies.trendingmovies.presentation.movieslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hany.tmdb_movies.trendingmovies.domain.repo.TrendingMoviesRepository
import com.hany.tmdb_movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: TrendingMoviesRepository
) : ViewModel() {

    var state by mutableStateOf(MovieListState())

    fun onEvent(event: MoviesListEvent) {
        when (event) {
            is MoviesListEvent.Refresh -> {
                getMoviesList(true)
            }
        }
    }

    init {
        getMoviesList()
    }

    private fun getMoviesList(
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getMovieList(fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(
                                movies = it
                            )
                        }
                    }
                    is Resource.Error -> {
                        state = MovieListState(
                            error = true,
                            errorMessage = result.message ?: "Unknown Message"
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

}