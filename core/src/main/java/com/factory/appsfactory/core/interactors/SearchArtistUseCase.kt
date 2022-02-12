package com.factory.appsfactory.core.interactors

import com.factory.appsfactory.core.data.ArtistRepository

class SearchArtistUseCase(private val artistRepository: ArtistRepository) {
    suspend operator fun invoke(artistName: String) = artistRepository.getArtistsByName(artistName)
}