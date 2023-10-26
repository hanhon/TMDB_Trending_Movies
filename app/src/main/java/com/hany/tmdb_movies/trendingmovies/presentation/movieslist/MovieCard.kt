package com.hany.tmdb_movies.trendingmovies.presentation.movieslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import com.hany.tmdb_movies.R
import com.hany.tmdb_movies.trendingmovies.domain.model.Movie
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.large
    ) {

        GlideImage(
            imageModel = movie.getFullImagePath(),
            contentScale = ContentScale.Crop,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colorScheme.onSurfaceVariant,
                highlightColor = Color.White,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f
            ),
            // shows error message when failed
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = movie.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = movie.year,
                modifier = Modifier.padding( top = 4.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}