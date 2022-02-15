package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class AlbumInfoModel(
    @SerializedName("name")
    val albumName: String,
    @SerializedName("artist")
    val artistName: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("playcount")
    val playCount: Long,
    @SerializedName("image")
    val imageList: List<ImageModel>,
    @SerializedName("tracks")
    val trackResponseModel: TrackResponseModel?,
    @SerializedName("url")
    val url: String,
)