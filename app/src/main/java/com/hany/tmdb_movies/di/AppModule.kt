package com.hany.tmdb_movies.di

import android.app.Application
import androidx.room.Room
import com.hany.tmdb_movies.trendingmovies.data.datasource.TrendingMoviesDatabase
import com.hany.tmdb_movies.trendingmovies.data.remote.TrendingTMDBAPI
import com.hany.tmdb_movies.trendingmovies.data.repository.TrendingMoviesRepositoryImpl
import com.hany.tmdb_movies.trendingmovies.domain.repo.TrendingMoviesRepository
import com.hany.tmdb_movies.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTMDBApi(): TrendingTMDBAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                        val originalHttpUrl = chain.request().url
                        val newUrl = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_key", Constants.API_KEY)
                            .build()
                        request.url(newUrl)
                        chain.proceed(request.build())
                    }.build()

            )
            .build()
            .create(TrendingTMDBAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): TrendingMoviesDatabase {
        return Room.databaseBuilder(
            app,
            TrendingMoviesDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepositoryApi(api: TrendingTMDBAPI, db: TrendingMoviesDatabase): TrendingMoviesRepository {
        return TrendingMoviesRepositoryImpl(api = api, db = db)
    }
}