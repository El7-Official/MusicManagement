package com.factory.appsfactory.challenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.factory.appsfactory.challenge.presentation.utils.CoroutineContextProvider
import com.factory.appsfactory.challenge.presentation.utils.UiAwareLiveData
import com.factory.appsfactory.challenge.presentation.utils.UiAwareModel
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.interactors.GetArtistAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class ArtistAlbumListUIModel : UiAwareModel() {
    object Loading : ArtistAlbumListUIModel()
    data class Error(var error: String = "") : ArtistAlbumListUIModel()
    data class Success(val data: List<Album>) : ArtistAlbumListUIModel()
}

@HiltViewModel
class ArtistAlbumListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getArtistAlbumsUseCase: GetArtistAlbumsUseCase
) : BaseViewModel(contextProvider) {

    private val _artistAlbumList = UiAwareLiveData<ArtistAlbumListUIModel>()
    private var artistAlbumList: LiveData<ArtistAlbumListUIModel> = _artistAlbumList

    fun getAlbumListLiveData(): LiveData<ArtistAlbumListUIModel> {
        return artistAlbumList
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _artistAlbumList.postValue(ArtistAlbumListUIModel.Error(exception.message ?: "Error"))
    }

    fun getArtistAlbumList(artist: Artist) {
        _artistAlbumList.postValue(ArtistAlbumListUIModel.Loading)
        launchCoroutineIO {
            loadArtistTopAlbums(artist)
        }
    }

    private suspend fun loadArtistTopAlbums(artist: Artist) {
        _artistAlbumList.postValue(ArtistAlbumListUIModel.Success(getArtistAlbumsUseCase(artist)))
    }
}