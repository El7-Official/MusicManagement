package com.factory.appsfactory.challenge.framework.db.models

import androidx.room.Embedded
import androidx.room.Relation

data class ArtistWithAlbums(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "ref",
        entityColumn = "artist_id"
    )
    val albums: List<Album>
)