package com.factory.appsfactory.challenge.framework.network.models

import com.google.gson.annotations.SerializedName

data class ArtistsResponseModel(@SerializedName("results") val result: Results)

data class Results(@SerializedName("artistmatches") val artistMatches: ArtistMatchesResponseModel)