package com.factory.appsfactory.core.domain

import java.io.Serializable

data class Artist(
    val id: String,
    val name: String,
    val url: String,
    val thumbnail: String
): Serializable