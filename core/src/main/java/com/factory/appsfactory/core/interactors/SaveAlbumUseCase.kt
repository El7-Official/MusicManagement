package com.factory.appsfactory.core.interactors

import com.factory.appsfactory.core.data.AlbumRepository
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.Artist
import javax.inject.Inject

class SaveAlbumUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(album: Album, artist: Artist) {
        albumRepository.addAlbum(album, artist)
    }
}