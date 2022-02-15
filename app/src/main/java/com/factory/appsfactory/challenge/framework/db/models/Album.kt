package com.factory.appsfactory.challenge.framework.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.factory.appsfactory.challenge.framework.db.utils.DbConstants

@Entity(tableName = DbConstants.ALBUM_TABLE_NAME)
class Album(
    @PrimaryKey
    val ref: String,
    val name: String,
    val url: String,
    val thumbnail: String,
    @ColumnInfo(name = "is_on_cache") val isOnCache: Boolean,
    @ColumnInfo(name = "play_count") val playCount: Long,
    @ColumnInfo(name = "artist_name") val artistName: String
)