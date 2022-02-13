package com.factory.appsfactory.challenge.framework.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
class Artist(
    @PrimaryKey
    val ref: String,
    val name: String,
    val url: String,
    val thumbnail: String
)
