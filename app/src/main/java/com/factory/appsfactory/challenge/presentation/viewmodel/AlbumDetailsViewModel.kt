package com.factory.appsfactory.challenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.factory.appsfactory.challenge.presentation.utils.CoroutineContextProvider
import com.factory.appsfactory.challenge.presentation.utils.UiAwareLiveData
import com.factory.appsfactory.challenge.presentation.utils.UiAwareModel
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.AlbumDetails
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.interactors.GetAlbumDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class AlbumDetailsUIModel : UiAwareModel() {
    object Loading : AlbumDetailsUIModel()
    data class Error(var error: String = "") : AlbumDetailsUIModel()
    data class Success(val data: AlbumDetails?) : AlbumDetailsUIModel()
}

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase
) : BaseViewModel(contextProvider) {

    private val _albumDetails = UiAwareLiveData<AlbumDetailsUIModel>()
    private var albumDetails: LiveData<AlbumDetailsUIModel> = _albumDetails

    fun getAlbumListLiveData(): LiveData<AlbumDetailsUIModel> {
        return albumDetails
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _albumDetails.postValue(AlbumDetailsUIModel.Error(exception.message ?: "Error"))
    }

    fun getAlbumDetails(fromCache: Boolean, artist: Artist, album: Album) {
        _albumDetails.postValue(AlbumDetailsUIModel.Loading)
        launchCoroutineIO {
            loadAlbumDetails(fromCache, artist, album)
        }
    }

    private suspend fun loadAlbumDetails(fromCache: Boolean, artist: Artist, album: Album) {
        _albumDetails.postValue(
            AlbumDetailsUIModel.Success(
                getAlbumDetailsUseCase(
                    fromCache,
                    album,
                    artist
                )
            )
        )
    }

}