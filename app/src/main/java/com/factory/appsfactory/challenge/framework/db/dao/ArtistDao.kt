package com.factory.appsfactory.challenge.framework.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.factory.appsfactory.challenge.framework.db.models.Artist
import com.factory.appsfactory.challenge.framework.db.models.ArtistWithAlbums

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artists WHERE ref=:ref")
    fun getArtistByRef(ref: String): Artist?

    @Transaction
    @Query("SELECT * FROM artists WHERE ref=:ref")
    fun getArtistWithAlbums(ref: String): ArtistWithAlbums?
}