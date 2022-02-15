package com.factory.appsfactory.challenge.ui.albumlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.factory.appsfactory.challenge.databinding.FragmentAlbumListBinding
import com.factory.appsfactory.challenge.presentation.extensions.observe
import com.factory.appsfactory.challenge.presentation.viewmodel.AlbumListUIModel
import com.factory.appsfactory.challenge.presentation.viewmodel.AlbumListViewModel
import com.factory.appsfactory.challenge.presentation.viewmodel.BaseViewModel
import com.factory.appsfactory.challenge.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumListFragment : BaseFragment<FragmentAlbumListBinding, BaseViewModel>() {

    lateinit var albumAdapter: AlbumAdapter

    override fun getViewBinding() = FragmentAlbumListBinding.inflate(layoutInflater)

    override val viewModel: AlbumListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeVM()
        fetchData()
    }

    private fun initUI() {
        setUIListener()
        initRecyclerView()
    }

    private fun observeVM() {
        observe(viewModel.getAlbumListLiveData(), ::onViewStateChange)
    }

    private fun fetchData() {
        viewModel.getAlbumList()
    }

    private fun initRecyclerView() {
        albumAdapter = AlbumAdapter()
        binding.recyclerViewAlbums.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        albumAdapter.setItemClickListener { album ->
            findNavController().navigate(
                AlbumListFragmentDirections.actionAlbumListFragmentToAlbumDetailsFragment(
                    true,
                    album
                )
            )
        }
    }

    private fun setUIListener() {
        binding.btnSearch.setOnClickListener {
            binding.txtInputEditTxtSearchArtist.text.toString().apply {
                if (this.isNotEmpty()) {
                    findNavController().navigate(
                        AlbumListFragmentDirections.actionAlbumListFragmentToArtistListFragment(
                            this
                        )
                    )
                }
            }
        }
    }

    private fun onViewStateChange(event: AlbumListUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is AlbumListUIModel.Error -> handleErrorMessage(event.error)
            is AlbumListUIModel.Loading -> handleLoading(true)
            is AlbumListUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    albumAdapter.list = it
                }
            }
        }
    }
}