package com.factory.appsfactory.challenge.ui.topalbums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.factory.appsfactory.challenge.databinding.FragmentTopAlbumsBinding
import com.factory.appsfactory.challenge.presentation.extensions.observe
import com.factory.appsfactory.challenge.presentation.viewmodel.*
import com.factory.appsfactory.challenge.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopAlbumsFragment : BaseFragment<FragmentTopAlbumsBinding, BaseViewModel>() {

    private val args: TopAlbumsFragmentArgs by navArgs()
    lateinit var topAlbumAdapter: TopAlbumsAdapter

    override fun getViewBinding() = FragmentTopAlbumsBinding.inflate(layoutInflater)

    override val viewModel: ArtistAlbumListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeVM()
        fetchData(args)
    }

    private fun initUI() {
        initRecyclerView()
    }

    private fun observeVM() {
        observe(viewModel.getAlbumListLiveData(), ::onViewStateChange)
    }

    private fun fetchData(args: TopAlbumsFragmentArgs) {
        args.artist?.let {
            viewModel.getArtistAlbumList(it)
        } ?: handleEmptyBundle()
    }

    private fun initRecyclerView() {
        topAlbumAdapter = TopAlbumsAdapter()
        binding.recyclerViewTopAlbums.apply {
            adapter = topAlbumAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        topAlbumAdapter.setItemClickListener { album ->
            args.artist?.let {
                findNavController().navigate(
                    TopAlbumsFragmentDirections.actionTopAlbumsFragmentToAlbumDetailsFragment(
                        false,
                        album
                    )
                )
            } ?: handleEmptyBundle()
        }
    }

    private fun onViewStateChange(event: ArtistAlbumListUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is ArtistAlbumListUIModel.Error -> {
                handleErrorMessage(event.error)
            }
            is ArtistAlbumListUIModel.Loading -> handleLoading(true)
            is ArtistAlbumListUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    topAlbumAdapter.list = it
                }
            }
        }
    }

    private fun handleEmptyBundle() {

    }
}