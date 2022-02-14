package com.factory.appsfactory.challenge.di

import com.factory.appsfactory.core.data.AlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAlbumRepository(albumRepository: AlbumRepository) = albumRepository
}