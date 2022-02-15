package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class ArtistModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("image")
    val image: List<ImageModel>?
)