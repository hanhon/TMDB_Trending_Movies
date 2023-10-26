package com.hany.tmdb_movies.trendingmovies.presentation.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hany.tmdb_movies.R
import com.hany.tmdb_movies.trendingmovies.domain.model.Movie
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Destination
@Composable
fun MovieDetailsScreen(
    movie: Movie,
    navigator: DestinationsNavigator,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.detailState.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            GlideImage(
                imageModel = state.details?.movie?.getFullImagePath(),
                contentScale = ContentScale.Crop,
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    highlightColor = Color.White,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),
                failure = {
                    Icon(
                        Icons.Outlined.Error,
                        contentDescription = stringResource(id = R.string.failed),
                        modifier = Modifier.align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "image request failed.",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 24.dp)
                    )
                },
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f),
            )


            state.details?.movie?.let {
                Text(
                    text = it.name,
                    modifier = Modifier.padding( top = 16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = movie.year,
                    modifier = Modifier.padding( top = 4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            state.details?.let {
                Text(
                    modifier = Modifier.padding( top = 16.dp),
                    text = it.summary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            }
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