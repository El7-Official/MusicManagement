package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class AlbumModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("playcount")
    val playcount: Long?,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("artist")
    val artist: ArtistModel,
    @SerializedName("image")
    val imageList: List<ImageModel>?
)