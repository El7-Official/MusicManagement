package com.factory.appsfactory.challenge.ui.albumlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.factory.appsfactory.challenge.databinding.FragmentAlbumListBinding
import com.factory.appsfactory.challenge.presentation.extensions.observe
import com.factory.appsfactory.challenge.presentation.viewmodel.AlbumListUIModel
import com.factory.appsfactory.challenge.presentation.viewmodel.AlbumListViewModel
import com.factory.appsfactory.challenge.presentation.viewmodel.BaseViewModel
import com.factory.appsfactory.challenge.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumListFragment : BaseFragment<FragmentAlbumListBinding, BaseViewModel>() {

    override fun getViewBinding() = FragmentAlbumListBinding.inflate(layoutInflater)

    override val viewModel: AlbumListViewModel by viewModels()

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

    private fun onViewStateChange(event: AlbumListUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is AlbumListUIModel.Error -> handleErrorMessage(event.error)
            is AlbumListUIModel.Loading -> handleLoading(true)
            is AlbumListUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    Log.e("TAG_BASE", "onViewStateChange-it: $it")
                }
            }
        }
    }
}