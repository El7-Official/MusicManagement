package com.factory.appsfactory.challenge.framework.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
class Album(
    @PrimaryKey
    val ref: String,
    val name: String,
    val url: String,
    val thumbnail: String,
    @ColumnInfo(name = "play_count") val playCount: Long,
    @ColumnInfo(name = "artist_id") val artistId: String
)