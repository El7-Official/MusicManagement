package com.factory.appsfactory.challenge.framework.network.repository

import com.factory.appsfactory.challenge.framework.network.api.LastFMService
import com.factory.appsfactory.core.data.ArtistDataSource
import com.factory.appsfactory.core.domain.Artist

class ArtistDataSourceImp (
    private val lastFMService: LastFMService
): ArtistDataSource {

    override suspend fun getArtistsByName(name: String): List<Artist> {
        return lastFMService.getArtistsByName(name).result.artistMatches.artists.map {
            Artist(
                it.mbid,
                it.name,
                it.url,
                ""
            )
        }
    }
}