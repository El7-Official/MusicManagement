package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.domain.Artist

interface ArtistDataSource {
    // Remote
    suspend fun getArtistsByName(name: String): List<Artist>
}