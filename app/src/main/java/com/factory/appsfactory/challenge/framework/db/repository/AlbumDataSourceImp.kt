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
        takeIf { album.id.isNotEmpty() && artist.id.isNotEmpty() }?.apply {
            albumDAO.addAlbum(
                DbAlbum(
                    album.id,
                    album.name,
                    album.url,
                    album.thumbnail,
                    album.playCount,
                    artist.id
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
        takeIf { artist.id.isNotEmpty() && album.id.isNotEmpty() }?.apply {
            albumDAO.deleteAlbum(
                DbAlbum(
                    album.id,
                    album.name,
                    album.url,
                    album.thumbnail,
                    album.playCount,
                    artist.id
                )
            )
        }
    }

    override suspend fun updateAlbumTracks(album: Album, track: Track) {

    }

    override suspend fun getAlbumDetails(album: Album, artist: Artist): AlbumDetails? {
        // Verify if album contains mbid
        // if empty return null
            // else
                // Get all albums & get album id == album param id
                    // Export artist id from album item
                        // Get artist details
                    // get album tracks
        return takeIf { album.id.isNotEmpty() }?.let {
            albumDAO.getAlbums().firstOrNull { it.ref == album.id }?.let { dbAlbum ->
                val albumItem = Album(
                    dbAlbum.ref,
                    dbAlbum.name,
                    dbAlbum.url,
                    dbAlbum.thumbnail,
                    dbAlbum.playCount
                )
                val dbArtistItem = artistDao.getArtistByRef(dbAlbum.ref)
                val artistItem = with(dbArtistItem) {
                    Artist(
                        this?.ref ?: dbAlbum.artistId,
                        this?.name ?: "",
                        this?.url ?: "",
                        this?.thumbnail ?: ""
                    )
                }
                val tracks = albumDAO.getAlbumWithTracks(albumItem.id)?.tracks?.map { dbTrack ->
                    Track(
                        dbTrack.name,
                        dbTrack.url,
                        dbTrack.duration
                    )
                } ?: emptyList()
                AlbumDetails(albumItem, artistItem, tracks)
            }
        }
    }
}