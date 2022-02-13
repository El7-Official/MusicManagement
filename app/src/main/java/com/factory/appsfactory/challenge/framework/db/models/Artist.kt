package com.factory.appsfactory.challenge.framework.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.factory.appsfactory.challenge.framework.db.utils.DbConstants

@Entity(tableName = DbConstants.ARTIST_TABLE_NAME)
class Artist(
    @PrimaryKey
    val ref: String,
    val name: String,
    val url: String,
    val thumbnail: String
)
