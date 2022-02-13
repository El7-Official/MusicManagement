package com.factory.appsfactory.challenge.framework.db.models

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumWithTracks(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "ref",
        entityColumn = "album_id"
    )
    val tracks: List<Track>
)