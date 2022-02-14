package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.domain.Artist
import javax.inject.Inject

class ArtistRepository @Inject constructor(var artistDataSource: ArtistDataSource) {

    suspend fun getArtistsByName(name: String): List<Artist> {
        return artistDataSource.getArtistsByName(name)
    }
}