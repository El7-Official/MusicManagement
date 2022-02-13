package com.factory.appsfactory.challenge.framework.db.dao

import androidx.room.*
import com.factory.appsfactory.challenge.framework.db.models.Album
import com.factory.appsfactory.challenge.framework.db.models.AlbumWithTracks

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums")
    fun getAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlbum(album: Album)

    @Delete
    fun deleteAlbum(album: Album)

    @Transaction
    @Query("SELECT * FROM albums WHERE ref=:ref")
    fun getAlbumWithTracks(ref: String): AlbumWithTracks?
}