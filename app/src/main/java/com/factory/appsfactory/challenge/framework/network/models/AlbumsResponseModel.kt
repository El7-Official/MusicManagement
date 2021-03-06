package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class AlbumsResponseModel(@SerializedName("topalbums") val album: AlbumResponseModel)

data class AlbumResponseModel(@SerializedName("album") val topAlbums: List<AlbumModel>)