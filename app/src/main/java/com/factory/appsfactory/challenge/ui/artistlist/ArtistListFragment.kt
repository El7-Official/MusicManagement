package com.factory.appsfactory.challenge.ui.artistlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.factory.appsfactory.challenge.databinding.FragmentArtistListBinding
import com.factory.appsfactory.challenge.ui.base.BaseFragment
import com.factory.appsfactory.challenge.presentation.extensions.observe
import com.factory.appsfactory.challenge.presentation.viewmodel.ArtistUIModel
import com.factory.appsfactory.challenge.presentation.viewmodel.ArtistsViewModel
import com.factory.appsfactory.challenge.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistListFragment : BaseFragment<FragmentArtistListBinding, BaseViewModel>() {

    private val args: ArtistListFragmentArgs by navArgs()
    lateinit var artistAdapter: ArtistAdapter

    override fun getViewBinding(): FragmentArtistListBinding =
        FragmentArtistListBinding.inflate(layoutInflater)

    override val viewModel: ArtistsViewModel by viewModels()

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
        observe(viewModel.getArtistsLiveData(), ::onViewStateChange)
    }

    private fun fetchData(args: ArtistListFragmentArgs) {
        viewModel.getArtistsByName(args.artistName)
    }

    private fun initRecyclerView() {
        artistAdapter = ArtistAdapter()
        binding.recyclerViewArtists.apply {
            adapter = artistAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        artistAdapter.setItemClickListener { artist ->
            findNavController().navigate(
                ArtistListFragmentDirections.actionArtistListFragmentToTopAlbumsFragment(
                    artist
                )
            )
        }
    }

    private fun onViewStateChange(event: ArtistUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is ArtistUIModel.Error -> handleErrorMessage(event.error)
            is ArtistUIModel.Loading -> handleLoading(true)
            is ArtistUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    artistAdapter.list = it
                }
            }
        }
    }
}