package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track

class AlbumRepository(var albumDataSource: AlbumDataSource) {

    suspend fun addAlbum(album: Album, artist: Artist) {
        albumDataSource.addAlbum(album, artist)
    }

    suspend fun getAlbums(): List<Album> = albumDataSource.getAlbums()

    suspend fun getAlbumsByArtist(artist: Artist): List<Album> {
        return albumDataSource.getAlbumsByArtist(artist)
    }

    suspend fun getAlbumDetails(album: Album, artist: Artist): AlbumDetails {
        return albumDataSource.getAlbumDetails(album, artist)
    }

    suspend fun isAlbumBookmarked(album: Album): Boolean = albumDataSource.isAlbumBookmarked(album)

    suspend fun removeAlbum(album: Album, artist: Artist) = albumDataSource.removeAlbum(album, artist)

    suspend fun updateAlbumTracks(album: Album, track: Track) {
        return albumDataSource.updateAlbumTracks(album, track)
    }
}