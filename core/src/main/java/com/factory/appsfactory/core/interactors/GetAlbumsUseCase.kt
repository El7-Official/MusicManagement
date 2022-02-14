package com.factory.appsfactory.core.interactors

import com.factory.appsfactory.core.data.AlbumRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke() = albumRepository.getAlbums()
}