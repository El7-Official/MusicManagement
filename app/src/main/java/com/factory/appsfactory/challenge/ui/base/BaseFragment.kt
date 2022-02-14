package com.factory.appsfactory.challenge.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.factory.appsfactory.challenge.presentation.viewmodel.BaseViewModel

abstract class BaseFragment<VB : ViewBinding, ViewModel : BaseViewModel> : Fragment() {

    protected lateinit var binding: VB
    protected abstract val viewModel: ViewModel

    abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = getViewBinding()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        handleLoading(false)
    }

    protected open fun handleLoading(isLoading: Boolean) {

    }

    protected open fun handleErrorMessage(message: String?) {

    }
}
