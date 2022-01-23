package com.yoyo.facerecognition.di

import android.content.Context
import com.yoyo.facerecognition.BaseApp
import com.yoyo.facerecognition.repo.IPicInfoRepo
import com.yoyo.facerecognition.repo.PicInfoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApp {
        return app as BaseApp
    }
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext app: Context): Context {
        return app
    }
}