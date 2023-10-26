package com.hany.tmdb_movies.trendingmovies.presentation.movieslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hany.tmdb_movies.trendingmovies.presentation.destinations.MovieDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination
@Composable
fun MovieListScreen(
    navigator: DestinationsNavigator,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    val state = viewModel.state

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(MoviesListEvent.Refresh)
            }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(200.dp),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                content = {
                    items(state.movies) { movie ->
                        MovieCard(
                            movie = movie,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigator.navigate(MovieDetailsScreenDestination(movie))
                                }
                                .padding(16.dp)
                        )
                    }
                }
            )
        }

        if (state.error) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            Box(
                modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.secondary
                    )
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}