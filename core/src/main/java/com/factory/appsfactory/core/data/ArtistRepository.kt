package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.domain.Artist

class ArtistRepository(var artistDataSource: ArtistDataSource) {

    suspend fun getArtistsByName(name: String): List<Artist> {
        return artistDataSource.getArtistsByName(name)
    }
}