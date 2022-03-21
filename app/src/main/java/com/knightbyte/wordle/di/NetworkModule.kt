package com.knightbyte.wordle.di

import com.knightbyte.wordle.network.services.WordleService
import com.knightbyte.wordle.utils.WORDLE_API_BASE
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
object NetworkModule {
    @Singleton
    @Provides
    fun provideDriveService(): WordleService {
        return Retrofit.Builder()
            .baseUrl(WORDLE_API_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .build()
            )
            .build()
            .create(WordleService::class.java)
    }
}