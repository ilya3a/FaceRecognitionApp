package com.yoyo.facerecognition.di

import android.content.Context
import com.yoyo.facerecognition.database.DatabaseRoom
import com.yoyo.facerecognition.database.dao.PicInfoDao
import com.yoyo.facerecognition.repo.IPicInfoRepo
import com.yoyo.facerecognition.repo.PicInfoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun providePicInfoDao(context: Context): PicInfoDao {
        return DatabaseRoom.getDatabase(context).picInfoDao()
    }

    @Singleton
    @Provides
    fun provideRepo(context: Context, picInfoDao: PicInfoDao): IPicInfoRepo {
        return PicInfoRepo(context, picInfoDao)
    }


}