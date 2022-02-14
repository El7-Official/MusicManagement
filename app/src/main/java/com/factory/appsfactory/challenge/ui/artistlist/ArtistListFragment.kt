package com.factory.appsfactory.challenge.ui.artistlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.factory.appsfactory.challenge.databinding.FragmentArtistListBinding
import com.factory.appsfactory.challenge.ui.base.BaseFragment
import com.factory.appsfactory.challenge.presentation.extensions.observe
import com.factory.appsfactory.challenge.presentation.viewmodel.ArtistUIModel
import com.factory.appsfactory.challenge.presentation.viewmodel.ArtistsViewModel
import com.factory.appsfactory.challenge.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistListFragment : BaseFragment<FragmentArtistListBinding, BaseViewModel>() {

    override fun getViewBinding(): FragmentArtistListBinding =
        FragmentArtistListBinding.inflate(layoutInflater)

    override val viewModel: ArtistsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getArtistsByName("Akon")
        initUI()
        observeVM()
    }

    private fun observeVM() {
        observe(viewModel.getArtistsLiveData(), ::onViewStateChange)
    }

    private fun initUI() {

    }

    private fun onViewStateChange(event: ArtistUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is ArtistUIModel.Error -> handleErrorMessage(event.error)
            is ArtistUIModel.Loading -> handleLoading(true)
            is ArtistUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    Log.e("TAG_BASE", "onViewStateChange-it: ${it.toString()}")
                }
            }
        }
    }
}