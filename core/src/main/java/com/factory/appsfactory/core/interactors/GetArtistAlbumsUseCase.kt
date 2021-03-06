package com.factory.appsfactory.core.interactors

import com.factory.appsfactory.core.data.AlbumRepository
import com.factory.appsfactory.core.domain.Artist
import javax.inject.Inject

class GetArtistAlbumsUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(artist: Artist) = albumRepository.getAlbumsByArtist(artist)
}