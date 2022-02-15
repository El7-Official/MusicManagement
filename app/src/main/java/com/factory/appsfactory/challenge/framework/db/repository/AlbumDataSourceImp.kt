package com.factory.appsfactory.challenge.framework.db.repository

import com.factory.appsfactory.challenge.framework.db.dao.AlbumDao
import com.factory.appsfactory.challenge.framework.db.dao.ArtistDao
import com.factory.appsfactory.core.data.local.LocalAlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.challenge.framework.db.models.Album as DbAlbum
import com.factory.appsfactory.challenge.framework.db.models.Artist as DbArtist
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track
import javax.inject.Inject

class AlbumDataSourceImp @Inject constructor(
    private val albumDAO: AlbumDao,
    private val artistDao: ArtistDao
) : LocalAlbumDataSource {

    override suspend fun addAlbum(album: Album, artist: Artist): Boolean {
        takeIf { album.id.isNotEmpty() && artist.id.isNotEmpty() }?.let {
            val albumRecord = albumDAO.addAlbum(
                DbAlbum(
                    album.id,
                    album.name,
                    album.url,
                    album.thumbnail,
                    true,
                    album.playCount,
                    artist.id
                )
            )
            val artistRecord = artistDao.addArtist(
                DbArtist(
                    artist.id,
                    artist.name,
                    artist.url,
                    artist.thumbnail
                )
            )
            return albumRecord > 0 && artistRecord > 0
        } ?: return false
    }

    override suspend fun getAlbums(): List<Album> {
        return albumDAO.getAlbums().takeIf { it.isNotEmpty() }?.map {
            Album(
                it.ref,
                it.name,
                it.url,
                it.thumbnail,
                it.playCount,
                it.isOnCache
            )
        } ?: emptyList()
    }

    override suspend fun isAlbumBookmarked(album: Album): Boolean {
        return true
    }

    override suspend fun removeAlbum(album: Album, artist: Artist): Boolean {
        takeIf { artist.id.isNotEmpty() && album.id.isNotEmpty() }?.let {
            val deleteCount = albumDAO.deleteAlbum(
                DbAlbum(
                    album.id,
                    album.name,
                    album.url,
                    album.thumbnail,
                    album.isOnCache,
                    album.playCount,
                    artist.id
                )
            )
            return deleteCount == 1
        } ?: return false
    }

    override suspend fun updateAlbumTracks(album: Album, track: Track) {

    }

    override suspend fun getAlbumDetails(album: Album, artist: Artist): AlbumDetails? {
        return takeIf { album.id.isNotEmpty() }?.let {
            albumDAO.getAlbums().firstOrNull { it.ref == album.id }?.let { dbAlbum ->

                val albumItem = Album(
                    dbAlbum.ref,
                    dbAlbum.name,
                    dbAlbum.url,
                    dbAlbum.thumbnail,
                    dbAlbum.playCount,
                    dbAlbum.isOnCache
                )
                val dbArtistItem = artistDao.getArtistByRef(dbAlbum.artistId)
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