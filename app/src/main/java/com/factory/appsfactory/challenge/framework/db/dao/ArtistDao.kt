package com.factory.appsfactory.challenge.framework.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.factory.appsfactory.challenge.framework.db.models.ArtistWithAlbums

@Dao
interface ArtistDao {

    @Transaction
    @Query("SELECT * FROM artists WHERE ref=:ref")
    fun getArtistWithAlbums(ref: String): ArtistWithAlbums?
}