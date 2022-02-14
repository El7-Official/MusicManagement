package com.factory.appsfactory.challenge.di

import com.factory.appsfactory.challenge.BuildConfig
import com.factory.appsfactory.challenge.framework.network.api.LastFMService
import com.factory.appsfactory.challenge.framework.network.api.RetrofitFactory
import com.factory.appsfactory.challenge.framework.network.repository.AlbumDataSourceImp
import com.factory.appsfactory.challenge.framework.network.repository.ArtistDataSourceImp
import com.factory.appsfactory.core.data.ArtistDataSource
import com.factory.appsfactory.core.data.remote.RemoteAlbumDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideLastFMService(): LastFMService {
        return RetrofitFactory.create(BuildConfig.DEBUG, BuildConfig.BASE_URL)
    }

    @Provides
    @Singleton
    fun provideAlbumDataSource(albumDataSourceImp: AlbumDataSourceImp): RemoteAlbumDataSource {
        return albumDataSourceImp
    }

    @Provides
    @Singleton
    fun provideArtistDataSource(artistDataSourceImp: ArtistDataSourceImp): ArtistDataSource {
        return artistDataSourceImp
    }
}