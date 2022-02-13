package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class ArtistMatchesResponseModel(@SerializedName("artist") val artists: List<ArtistModel>)
