package com.factory.appsfactory.core.data.remote

import com.factory.appsfactory.core.data.AlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.Artist

interface RemoteAlbumDataSource: AlbumDataSource {

    // Remote
    suspend fun getAlbumsByArtist(artist: Artist): List<Album>
}