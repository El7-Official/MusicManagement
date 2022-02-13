package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.domain.Track

interface AlbumDataSource {
    // Remote & Cache
    suspend fun getAlbumDetails(album: Album, artist: Artist): AlbumDetails
}