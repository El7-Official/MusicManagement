package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track

interface AlbumDataSource {

    // Cache
    suspend fun addAlbum(album: Album, artist: Artist)

    suspend fun getAlbums(): List<Album>

    suspend fun isAlbumBookmarked(album: Album): Boolean

    suspend fun removeAlbum(album: Album, artist: Artist)

    suspend fun updateAlbumTracks(album: Album, track: Track)

    // Remote
    suspend fun getAlbumsByArtist(artist: Artist): List<Album>

    // Remote & Cache
    suspend fun getAlbumDetails(album: Album, artist: Artist): AlbumDetails
}