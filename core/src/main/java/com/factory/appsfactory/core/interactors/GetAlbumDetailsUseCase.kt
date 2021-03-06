package com.factory.appsfactory.core.interactors

import com.factory.appsfactory.core.data.AlbumRepository
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(fromCache: Boolean, album: Album): AlbumDetails? {
        return albumRepository.getAlbumDetails(fromCache, album)
    }
}