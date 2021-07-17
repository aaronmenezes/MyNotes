package com.kyser.mynotes.di

import android.content.Context
import androidx.room.Room
import com.kyser.mynotes.cache.CacheDb
import com.kyser.mynotes.cache.NoteCacheDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideCacheDb(@ApplicationContext context: Context):CacheDb{
        return Room.databaseBuilder(
            context,
            CacheDb::class.java,
            CacheDb.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideCacheDao(cacheDb: CacheDb):NoteCacheDao{
        return cacheDb.cacheDao()
    }
}