package com.factory.appsfactory.core.interactors

import com.factory.appsfactory.core.data.ArtistRepository
import javax.inject.Inject

class SearchArtistUseCase @Inject constructor (private val artistRepository: ArtistRepository) {
    suspend operator fun invoke(artistName: String) = artistRepository.getArtistsByName(artistName)
}