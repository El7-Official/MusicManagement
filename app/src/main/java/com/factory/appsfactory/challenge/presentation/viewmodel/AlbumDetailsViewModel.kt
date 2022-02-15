package com.factory.appsfactory.challenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.factory.appsfactory.challenge.presentation.utils.CoroutineContextProvider
import com.factory.appsfactory.challenge.presentation.utils.UiAwareLiveData
import com.factory.appsfactory.challenge.presentation.utils.UiAwareModel
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.interactors.DeleteAlbumUseCase
import com.factory.appsfactory.core.interactors.GetAlbumDetailsUseCase
import com.factory.appsfactory.core.interactors.SaveAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class AlbumDetailsUIModel : UiAwareModel() {
    object Loading : AlbumDetailsUIModel()
    data class Error(var error: String = "") : AlbumDetailsUIModel()
    data class Success(val data: AlbumDetails?) : AlbumDetailsUIModel()
    data class FavoriteStatus(val favorite: Favorite, val status: Boolean) :
        AlbumDetailsUIModel()
}

enum class Favorite {
    IN_FAVORITE,
    OUT_FAVOURITE
}


@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
    private val saveAlbumUseCase: SaveAlbumUseCase,
    private val deleteAlbumUseCase: DeleteAlbumUseCase
) : BaseViewModel(contextProvider) {

    private val _albumDetails = UiAwareLiveData<AlbumDetailsUIModel>()
    private var albumDetails: LiveData<AlbumDetailsUIModel> = _albumDetails

    fun getAlbumListLiveData(): LiveData<AlbumDetailsUIModel> {
        return albumDetails
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _albumDetails.postValue(AlbumDetailsUIModel.Error(exception.message ?: "Error"))
    }

    fun getAlbumDetails(fromCache: Boolean, album: Album) {
        _albumDetails.postValue(AlbumDetailsUIModel.Loading)
        launchCoroutineIO {
            loadAlbumDetails(fromCache, album)
        }
    }

    fun addAlbumInFavourite(album: Album) {
        launchCoroutineIO {
            saveAlbumUseCase(album).let {
                _albumDetails.postValue(
                    AlbumDetailsUIModel.FavoriteStatus(
                        Favorite.IN_FAVORITE,
                        it
                    )
                )
            }
        }
    }

    fun removeAlbumFromFavourite(albumId: String) {
        launchCoroutineIO {
            deleteAlbumUseCase(albumId).let {
                _albumDetails.postValue(
                    AlbumDetailsUIModel.FavoriteStatus(
                        Favorite.OUT_FAVOURITE,
                        it
                    )
                )
            }
        }
    }

    private suspend fun loadAlbumDetails(fromCache: Boolean, album: Album) {
        _albumDetails.postValue(
            AlbumDetailsUIModel.Success(
                getAlbumDetailsUseCase(
                    fromCache,
                    album
                )
            )
        )
    }

}