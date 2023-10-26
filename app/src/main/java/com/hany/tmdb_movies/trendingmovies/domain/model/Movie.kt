package com.hany.tmdb_movies.trendingmovies.domain.model

import android.os.Parcelable
import com.hany.tmdb_movies.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val name: String,
    val imageUrl: String,
    val year: String,
    val movieID: Int
) : Parcelable {
    fun getFullImagePath(): String {
        return Constants.IMAGE_URL + imageUrl
    }
}
