package com.factory.appsfactory.core.domain

data class AlbumDetails(
    val album: Album,
    val artist: Artist,
    val tracks: List<Track>
)