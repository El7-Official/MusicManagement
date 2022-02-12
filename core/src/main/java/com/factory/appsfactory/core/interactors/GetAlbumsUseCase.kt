package com.factory.appsfactory.core.interactors

import com.factory.appsfactory.core.data.AlbumRepository

class GetAlbumsUseCase(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke() = albumRepository.getAlbums()
}