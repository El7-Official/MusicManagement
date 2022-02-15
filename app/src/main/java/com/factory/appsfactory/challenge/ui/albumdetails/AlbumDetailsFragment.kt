package com.factory.appsfactory.challenge.ui.albumdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.factory.appsfactory.challenge.databinding.FragmentAlbumDetailsBinding
import com.factory.appsfactory.challenge.presentation.extensions.observe
import com.factory.appsfactory.challenge.presentation.viewmodel.*
import com.factory.appsfactory.challenge.ui.base.BaseFragment
import com.factory.appsfactory.core.domain.AlbumDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailsFragment : BaseFragment<FragmentAlbumDetailsBinding, BaseViewModel>() {

    private val args: AlbumDetailsFragmentArgs by navArgs()
    override fun getViewBinding() = FragmentAlbumDetailsBinding.inflate(layoutInflater)

    override val viewModel: AlbumDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeVM()
        fetchData(args)
    }

    private fun initUI() {

    }

    private fun observeVM() {
        observe(viewModel.getAlbumListLiveData(), ::onViewStateChange)
    }

    private fun fetchData(args: AlbumDetailsFragmentArgs) {
        args.album?.let { album ->
            args.artist?.let { artist ->
            viewModel.getAlbumDetails(args.fromCache, artist, album)
            }
        } ?: handleEmptyData()
    }

    private fun onViewStateChange(event: AlbumDetailsUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is AlbumDetailsUIModel.Error -> handleErrorMessage(event.error)
            is AlbumDetailsUIModel.Loading -> handleLoading(true)
            is AlbumDetailsUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    it?.let {
                        matchDataToUI(it)
                    } ?: handleEmptyData()
                }
            }
        }
    }

    private fun matchDataToUI(albumDetails: AlbumDetails) {
        with(binding.includeItemDetails) {
            txtViewArtistName.text = albumDetails.artist.name
            txtViewAlbumNameContent.text = albumDetails.album.name
            txtViewPlayCountContent.text = albumDetails.album.playCount.toString()
            txtViewLinkLabel.text = albumDetails.album.url
            txtViewTrackContent.text = albumDetails.tracks.takeIf { it.isNotEmpty() }?.joinToString(separator = " \t-\t ") ?: ""
        }
    }

    private fun handleEmptyData() {

    }
}