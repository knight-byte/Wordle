package com.knightbyte.wordle.di

import android.content.Context
import com.knightbyte.wordle.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Main app provider
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context):BaseApplication {
        return app as BaseApplication
    }
}