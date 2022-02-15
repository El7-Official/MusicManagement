package com.factory.appsfactory.challenge.framework.network.repository

import com.factory.appsfactory.challenge.framework.network.api.LastFMService
import com.factory.appsfactory.core.data.ArtistDataSource
import com.factory.appsfactory.core.domain.Artist
import java.util.*
import javax.inject.Inject

class ArtistDataSourceImp @Inject constructor(
    private val lastFMService: LastFMService
): ArtistDataSource {

    override suspend fun getArtistsByName(name: String): List<Artist> {
        return lastFMService.getArtistsByName(name).result.artistMatches.artists.map {
            Artist(
                it.mbid ?: UUID.randomUUID().toString(),
                it.name ?: "",
                it.url ?: "",
                ""
            )
        }
    }
}