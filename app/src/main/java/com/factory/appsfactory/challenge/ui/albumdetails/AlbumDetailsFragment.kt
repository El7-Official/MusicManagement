package com.factory.appsfactory.challenge.ui.albumdetails

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
class AlbumDetailsFragment : BaseFragment<FragmentTopAlbumsBinding, BaseViewModel>() {

    override fun getViewBinding() = FragmentTopAlbumsBinding.inflate(layoutInflater)

    override val viewModel: AlbumDetailsViewModel by viewModels()

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

    private fun onViewStateChange(event: AlbumDetailsUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is AlbumDetailsUIModel.Error -> handleErrorMessage(event.error)
            is AlbumDetailsUIModel.Loading -> handleLoading(true)
            is AlbumDetailsUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    Log.e("TAG_BASE", "onViewStateChange-it: $it")
                }
            }
        }
    }
}