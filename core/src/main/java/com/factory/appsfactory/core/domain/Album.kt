package com.factory.appsfactory.core.domain

import java.io.Serializable

data class Album(
    val id: String,
    val name: String,
    val url: String,
    val thumbnail: String,
    val playCount: Long
): Serializable
