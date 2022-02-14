package com.factory.appsfactory.challenge.ui.topalbums

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.factory.appsfactory.challenge.databinding.FragmentTopAlbumsBinding
import com.factory.appsfactory.challenge.presentation.extensions.observe
import com.factory.appsfactory.challenge.presentation.viewmodel.*
import com.factory.appsfactory.challenge.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopAlbumsFragment : BaseFragment<FragmentTopAlbumsBinding, BaseViewModel>() {

    override fun getViewBinding() = FragmentTopAlbumsBinding.inflate(layoutInflater)

    override val viewModel: ArtistAlbumListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeVM()
    }

    private fun observeVM() {
        observe(viewModel.getAlbumListLiveData(), ::onViewStateChange)
    }

    private fun initUI() {

    }

    private fun onViewStateChange(event: ArtistAlbumListUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is ArtistAlbumListUIModel.Error -> handleErrorMessage(event.error)
            is ArtistAlbumListUIModel.Loading -> handleLoading(true)
            is ArtistAlbumListUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    Log.e("TAG_BASE", "onViewStateChange-it: $it")
                }
            }
        }
    }
}