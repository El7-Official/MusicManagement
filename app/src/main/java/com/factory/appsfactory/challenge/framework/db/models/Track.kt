package com.factory.appsfactory.challenge.framework.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tracks")
class Track(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val url: String,
    val duration: Long,
    @ColumnInfo(name = "album_id") val albumId: String
)