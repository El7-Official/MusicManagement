package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class ImageModel(
    @SerializedName("#text")
    val text: String,
    @SerializedName("size")
    val size: String
)