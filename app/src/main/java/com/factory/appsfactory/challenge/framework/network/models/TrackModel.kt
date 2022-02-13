package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class TrackModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("duration")
    val duration: Long
)