package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.data.local.LocalAlbumDataSource
import com.factory.appsfactory.core.data.remote.RemoteAlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    var localAlbumDataSource: LocalAlbumDataSource,
    var remoteAlbumDataSource: RemoteAlbumDataSource
    ) {

    suspend fun addAlbum(album: Album): Boolean {
        return localAlbumDataSource.addAlbum(album)
    }

    suspend fun getAlbums(): List<Album> = localAlbumDataSource.getAlbums()

    suspend fun getAlbumsByArtist(artist: Artist): List<Album> {
        return remoteAlbumDataSource.getAlbumsByArtist(artist)
    }

    suspend fun getAlbumDetails(fromCache: Boolean, album: Album): AlbumDetails? {
        return if (fromCache) {
            localAlbumDataSource.getAlbumDetails(album)
        } else {
            remoteAlbumDataSource.getAlbumDetails(album)
        }
    }

    suspend fun isAlbumBookmarked(album: Album): Boolean = localAlbumDataSource.isAlbumBookmarked(album)

    suspend fun removeAlbum(albumId: String): Boolean = localAlbumDataSource.removeAlbum(albumId)

    suspend fun updateAlbumTracks(album: Album, track: Track) {
        return localAlbumDataSource.updateAlbumTracks(album, track)
    }
}