package com.factory.appsfactory.challenge.framework.network.repository

import com.factory.appsfactory.challenge.framework.network.api.LastFMService
import com.factory.appsfactory.core.data.remote.RemoteAlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track
import java.util.*
import javax.inject.Inject

class AlbumDataSourceImp @Inject constructor(
    private val lastFMService: LastFMService
) : RemoteAlbumDataSource {

    override suspend fun getAlbumsByArtist(artist: Artist): List<Album> {
        return takeIf { artist.name.isNotEmpty() }?.let {
            lastFMService.getArtistAlbums(artist.name).album.topAlbums.map {
                Album(
                    it.mbid ?: UUID.randomUUID().toString(),
                    it.name ?: "",
                    it.url ?: "",
                    "",
                    it.playcount ?: 0,
                    artistName = artist.name
                )
            }
        } ?: emptyList()
    }

    override suspend fun getAlbumDetails(album: Album): AlbumDetails? {
        return takeIf { album.name.isNotEmpty()  }?.let {
            with(lastFMService.getAlbumDetails(album.artistName, album.name).albumInfo) {
                val trackList = this.trackResponseModel?.trackList?.map {
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
                    if (this.playCount == 0L) album.playCount else this.playCount,
                    artistName = if (this.artistName.isEmpty()) album.artistName else this.artistName
                )
                AlbumDetails(
                    albumItem,
                    trackList
                )
            }
        }
    }
}