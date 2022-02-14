package com.factory.appsfactory.challenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.factory.appsfactory.challenge.presentation.utils.CoroutineContextProvider
import com.factory.appsfactory.challenge.presentation.utils.UiAwareLiveData
import com.factory.appsfactory.challenge.presentation.utils.UiAwareModel
import com.factory.appsfactory.core.domain.Artist
import com.factory.appsfactory.core.interactors.SearchArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class ArtistUIModel : UiAwareModel() {
    object Loading : ArtistUIModel()
    data class Error(var error: String = "") : ArtistUIModel()
    data class Success(val data: List<Artist>) : ArtistUIModel()
}

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val searchArtistUseCase: SearchArtistUseCase
) : BaseViewModel(contextProvider) {

    private val _artistList = UiAwareLiveData<ArtistUIModel>()
    private var artistList: LiveData<ArtistUIModel> = _artistList

    fun getArtistsLiveData(): LiveData<ArtistUIModel> {
        return artistList
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _artistList.postValue(ArtistUIModel.Error(exception.message ?: "Error"))
    }

    fun getArtistsByName(artistName: String) {
        _artistList.postValue(ArtistUIModel.Loading)
        launchCoroutineIO {
            loadArtists(artistName)
        }
    }

    private suspend fun loadArtists(artistName: String) {
        _artistList.postValue(ArtistUIModel.Success(searchArtistUseCase(artistName)))
    }
}