package com.kyser.mynotes.di

import com.kyser.mynotes.cache.CacheMapper
import com.kyser.mynotes.cache.NoteCacheDao
import com.kyser.mynotes.repository.MainRepository
import com.kyser.mynotes.service.NetworkMapper
import com.kyser.mynotes.service.NotesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository( cacheDao: NoteCacheDao,
                               notesService: NotesService,
                               cacheMapper: CacheMapper,
                               networkMapper: NetworkMapper
                                ):MainRepository{
                                         return MainRepository(cacheDao,notesService,cacheMapper,networkMapper)
                                   }
}