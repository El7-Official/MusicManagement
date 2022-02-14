package com.factory.appsfactory.challenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.factory.appsfactory.challenge.presentation.utils.CoroutineContextProvider
import com.factory.appsfactory.challenge.presentation.utils.UiAwareLiveData
import com.factory.appsfactory.challenge.presentation.utils.UiAwareModel
import com.factory.appsfactory.core.domain.Album
import com.factory.appsfactory.core.interactors.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class AlbumListUIModel : UiAwareModel() {
    object Loading : AlbumListUIModel()
    data class Error(var error: String = "") : AlbumListUIModel()
    data class Success(val data: List<Album>) : AlbumListUIModel()
}

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getAlbumLisUseCase: GetAlbumsUseCase
) : BaseViewModel(contextProvider) {

    private val _albumList = UiAwareLiveData<AlbumListUIModel>()
    private var albumList: LiveData<AlbumListUIModel> = _albumList

    fun getAlbumListLiveData(): LiveData<AlbumListUIModel> {
        return albumList
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _albumList.postValue(AlbumListUIModel.Error(exception.message ?: "Error"))
    }

    fun getAlbumList() {
        _albumList.postValue(AlbumListUIModel.Loading)
        launchCoroutineIO {
            loadAlbums()
        }
    }

    private suspend fun loadAlbums() {
        _albumList.postValue(AlbumListUIModel.Success(getAlbumLisUseCase()))
    }
}