package com.factory.appsfactory.challenge.framework.network.repository

import com.factory.appsfactory.challenge.framework.network.api.LastFMService
import com.factory.appsfactory.core.data.remote.RemoteAlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track

class AlbumDataSourceImp (
    private val lastFMService: LastFMService
): RemoteAlbumDataSource {

    override suspend fun getAlbumsByArtist(artist: Artist): List<Album> {
        return lastFMService.getArtistAlbums(artist.name).topAlbums.map {
            Album(
                it.mbid,
                it.name,
                it.url,
                "",
                it.playcount
            )
        }
    }

    override suspend fun getAlbumDetails(album: Album, artist: Artist): AlbumDetails {
        return with(lastFMService.getAlbumDetails(artist.name, album.name).albumInfo) {
            val trackList = this.trackList?.map {
                Track(
                    it.name,
                    it.url,
                    it.duration
                )
            } ?: emptyList()
            AlbumDetails(
                album,
                artist,
                trackList
            )
        }
    }
}