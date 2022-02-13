package com.factory.appsfactory.challenge.framework.network.repository

import com.factory.appsfactory.challenge.framework.network.api.LastFMService
import com.factory.appsfactory.core.data.remote.RemoteAlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track

class AlbumDataSourceImp(
    private val lastFMService: LastFMService
) : RemoteAlbumDataSource {

    override suspend fun getAlbumsByArtist(artist: Artist): List<Album> {
        return takeIf { artist.name.isNotEmpty() }?.let {
            lastFMService.getArtistAlbums(artist.name).topAlbums.map {
                Album(
                    it.mbid,
                    it.name,
                    it.url,
                    "",
                    it.playcount
                )
            }
        } ?: emptyList()
    }

    override suspend fun getAlbumDetails(album: Album, artist: Artist): AlbumDetails? {
        return takeIf { album.name.isNotEmpty() && artist.name.isNotEmpty() }?.let {
            with(lastFMService.getAlbumDetails(artist.name, album.name).albumInfo) {
                val trackList = this.trackList?.map {
                    Track(
                        it.name,
                        it.url,
                        it.duration
                    )
                } ?: emptyList()
                val albumItem = Album(
                    if (this.mbid.isEmpty()) album.id else this.mbid,
                    if (this.albumName.isEmpty()) album.name else this.albumName,
                    if (this.url.isEmpty()) album.url else this.url,
                    "",
                    if (this.playCount == 0L) album.playCount else this.playCount
                )
                AlbumDetails(
                    albumItem,
                    artist,
                    trackList
                )
            }
        }
    }
}