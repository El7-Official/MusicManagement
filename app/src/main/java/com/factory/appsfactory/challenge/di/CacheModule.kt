package com.factory.appsfactory.challenge.di

import android.content.Context
import com.factory.appsfactory.challenge.framework.db.LastFMDatabase
import com.factory.appsfactory.challenge.framework.db.dao.AlbumDao
import com.factory.appsfactory.challenge.framework.db.dao.ArtistDao
import com.factory.appsfactory.challenge.framework.db.repository.AlbumDataSourceImp
import com.factory.appsfactory.core.data.local.LocalAlbumDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): LastFMDatabase {
        return LastFMDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideArtistDao(lastFMDatabase: LastFMDatabase): ArtistDao {
        return lastFMDatabase.artistDao()
    }

    @Provides
    @Singleton
    fun provideAlbumDao(lastFMDatabase: LastFMDatabase): AlbumDao {
        return lastFMDatabase.albumDao()
    }

    @Provides
    @Singleton
    fun provideCharacterCache(albumDataSourceImp: AlbumDataSourceImp): LocalAlbumDataSource {
        return albumDataSourceImp
    }
}