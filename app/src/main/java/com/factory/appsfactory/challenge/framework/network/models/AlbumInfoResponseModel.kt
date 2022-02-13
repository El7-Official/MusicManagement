package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class AlbumInfoResponseModel(@SerializedName("album") val albumInfo: AlbumInfoModel)