package com.factory.appsfactory.challenge.ui.albumdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.factory.appsfactory.challenge.R
import com.factory.appsfactory.challenge.databinding.FragmentAlbumDetailsBinding
import com.factory.appsfactory.challenge.presentation.extensions.observe
import com.factory.appsfactory.challenge.presentation.extensions.showSnackBar
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
        setUIListener()
        observeVM()
        fetchData(args)
    }

    private fun initUI() {

    }

    private fun setUIListener() {
        binding.includeItemDetails.checkboxFavourite.setOnCheckedChangeListener { view, isChecked ->
            if (!binding.includeItemDetails.checkboxFavourite.isPressed) {
                return@setOnCheckedChangeListener
            }
            if (isChecked) handleAddAlbum(args)
            else handleDeleteAlbum(args)
        }
    }

    private fun observeVM() {
        observe(viewModel.getAlbumListLiveData(), ::onViewStateChange)
    }

    private fun fetchData(args: AlbumDetailsFragmentArgs) {
        args.album?.let { album ->
            viewModel.getAlbumDetails(args.fromCache, album)
        } ?: handleEmptyData()
    }

    private fun handleAddAlbum(args: AlbumDetailsFragmentArgs) {
        args.album?.let { album ->
            viewModel.addAlbumInFavourite(album)
        }
    }

    private fun handleDeleteAlbum(args: AlbumDetailsFragmentArgs) {
        args.album?.let { album ->
            viewModel.removeAlbumFromFavourite(album.id)
        }
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
            is AlbumDetailsUIModel.FavoriteStatus -> {
                when (event.favorite) {
                    Favorite.IN_FAVORITE ->
                        if (event.status) {
                            showSnackBar(binding.root, getString(R.string.msg_success_add_to_favorite))
                        } else {
                            handleErrorMessage(getString(R.string.msg_failed_add_to_favorite))
                        }
                    Favorite.OUT_FAVOURITE ->
                        if (event.status) {
                            showSnackBar(binding.root, getString(R.string.msg_success_out_of_favorite))
                        } else {
                            handleErrorMessage(getString(R.string.msg_failed_out_of_favorite))
                        }
                }
            }
        }
    }

    private fun matchDataToUI(albumDetails: AlbumDetails) {
        with(binding.includeItemDetails) {
            checkboxFavourite.isChecked = albumDetails.album.isOnCache
            txtViewArtistName.text = albumDetails.album.artistName
            txtViewAlbumNameContent.text = albumDetails.album.name
            txtViewPlayCountContent.text = albumDetails.album.playCount.toString()
            txtViewLinkLabel.text = albumDetails.album.url
            txtViewTrackContent.text =
                albumDetails.tracks.takeIf { it.isNotEmpty() }?.joinToString(separator = " \t-\t ")
                    ?: ""
        }
    }

    private fun handleEmptyData() {

    }
}