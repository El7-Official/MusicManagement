package com.factory.appsfactory.challenge.framework.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.factory.appsfactory.challenge.framework.db.utils.DbConstants
import java.util.*

@Entity(tableName = DbConstants.TRACK_TABLE_NAME)
class Track(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val url: String,
    val duration: Long,
    @ColumnInfo(name = "album_id") val albumId: String
)