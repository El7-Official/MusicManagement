package com.factory.appsfactory.core.data.local

import com.factory.appsfactory.core.data.AlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track

interface LocalAlbumDataSource: AlbumDataSource {

    // Cache
    suspend fun addAlbum(album: Album, artist: Artist): Boolean

    suspend fun getAlbums(): List<Album>

    suspend fun isAlbumBookmarked(album: Album): Boolean

    suspend fun removeAlbum(album: Album, artist: Artist): Boolean

    suspend fun updateAlbumTracks(album: Album, track: Track)
}