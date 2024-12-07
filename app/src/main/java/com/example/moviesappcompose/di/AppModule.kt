package com.example.moviesappcompose.di

import android.content.Context
import androidx.room.Room
import com.example.moviesappcompose.data.local.AppDatabase
import com.example.moviesappcompose.data.local.MoviesDao
import com.example.moviesappcompose.data.remote.MoviesApiManager
import com.example.moviesappcompose.domain.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesNewsApiManager(retrofit: Retrofit): MoviesApiManager =
        retrofit.create(MoviesApiManager::class.java)

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context = context,
            name = "movies_database",
            klass = AppDatabase::class.java
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesMoviesDao(appDatabase: AppDatabase): MoviesDao = appDatabase.getMoviesDao()
}