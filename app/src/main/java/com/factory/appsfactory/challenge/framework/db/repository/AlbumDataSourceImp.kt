package com.factory.appsfactory.challenge.framework.db.repository

import com.factory.appsfactory.challenge.framework.db.dao.AlbumDao
import com.factory.appsfactory.challenge.framework.db.dao.ArtistDao
import com.factory.appsfactory.core.data.local.LocalAlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.challenge.framework.db.models.Album as DbAlbum
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track
import javax.inject.Inject

class AlbumDataSourceImp @Inject constructor (
    private val albumDAO: AlbumDao,
    private val artistDao: ArtistDao
) : LocalAlbumDataSource {

    override suspend fun addAlbum(album: Album, artist: Artist) {
        artist.takeIf { it.id.isNotEmpty() }?.apply {
            albumDAO.addAlbum(
                DbAlbum(
                    album.id,
                    album.name,
                    album.url,
                    album.thumbnail,
                    album.playCount,
                    this.id
                )
            )
        }
    }

    override suspend fun getAlbums(): List<Album> {
        return albumDAO.getAlbums().takeIf { it.isNotEmpty() }?.map {
            Album(
                it.ref,
                it.name,
                it.url,
                it.thumbnail,
                it.playCount
            )
        } ?: emptyList()
    }

    override suspend fun isAlbumBookmarked(album: Album): Boolean {
        return true
    }

    override suspend fun removeAlbum(album: Album, artist: Artist) {
        artist.takeIf { it.id.isNotEmpty() }?.apply {
            albumDAO.deleteAlbum(
                DbAlbum(
                    album.id,
                    album.name,
                    album.url,
                    album.thumbnail,
                    album.playCount,
                    this.id
                )
            )
        }
    }

    override suspend fun updateAlbumTracks(album: Album, track: Track) {

    }

    override suspend fun getAlbumDetails(album: Album, artist: Artist): AlbumDetails? {
        var artistItem: Artist?
        var albumItem: Album?
        var tracks: List<Track>
        return artistDao.getArtistWithAlbums(artist.id)?.let {
            artistItem = Artist(
                it.artist.ref,
                it.artist.name,
                it.artist.url,
                it.artist.thumbnail
            )
            it.albums.firstOrNull { it.ref == album.id }?.let { _album ->
                albumItem = Album(
                    _album.ref,
                    _album.name,
                    _album.url,
                    _album.thumbnail,
                    _album.playCount
                )
                tracks = albumDAO.getAlbumWithTracks(_album.ref)?.tracks?.map { _track ->
                    Track(
                        _track.name,
                        _track.url,
                        _track.duration
                    )
                } ?: emptyList()

                AlbumDetails(albumItem!!, artistItem!!, tracks)
            }
        }
    }
}