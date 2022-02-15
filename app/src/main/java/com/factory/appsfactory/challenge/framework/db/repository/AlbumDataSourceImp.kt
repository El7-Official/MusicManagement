package com.factory.appsfactory.challenge.framework.db.repository

import com.factory.appsfactory.challenge.framework.db.dao.AlbumDao
import com.factory.appsfactory.core.data.local.LocalAlbumDataSource
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.challenge.framework.db.models.Album as DbAlbum
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track
import javax.inject.Inject

class AlbumDataSourceImp @Inject constructor(
    private val albumDAO: AlbumDao,
) : LocalAlbumDataSource {

    override suspend fun addAlbum(album: Album): Boolean {
        takeIf { album.id.isNotEmpty() }?.let {
            val albumRecord = albumDAO.addAlbum(
                DbAlbum(
                    album.id,
                    album.name,
                    album.url,
                    album.thumbnail,
                    true,
                    album.playCount,
                    album.artistName
                )
            )
            return albumRecord > 0
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
                it.isOnCache,
                it.artistName
            )
        } ?: emptyList()
    }

    override suspend fun isAlbumBookmarked(album: Album): Boolean {
        return true
    }

    override suspend fun removeAlbum(albumId: String): Boolean {
        takeIf { albumId.isNotEmpty() }?.let {
            albumDAO.getAlbums().firstOrNull { album -> album.ref == albumId }?.let {
                val deleteCount = albumDAO.deleteAlbum(it)
                return deleteCount == 1
            }
        } ?: return false
    }

    override suspend fun updateAlbumTracks(album: Album, track: Track) {

    }

    override suspend fun getAlbumDetails(album: Album): AlbumDetails? {
        return takeIf { album.id.isNotEmpty() }?.let {
            albumDAO.getAlbums().firstOrNull { it.ref == album.id }?.let { dbAlbum ->

                val albumItem = Album(
                    dbAlbum.ref,
                    dbAlbum.name,
                    dbAlbum.url,
                    dbAlbum.thumbnail,
                    dbAlbum.playCount,
                    dbAlbum.isOnCache,
                    dbAlbum.artistName
                )
                val tracks = albumDAO.getAlbumWithTracks(albumItem.id)?.tracks?.map { dbTrack ->
                    Track(
                        dbTrack.name,
                        dbTrack.url,
                        dbTrack.duration
                    )
                } ?: emptyList()
                AlbumDetails(albumItem, tracks)
            }
        }
    }
}