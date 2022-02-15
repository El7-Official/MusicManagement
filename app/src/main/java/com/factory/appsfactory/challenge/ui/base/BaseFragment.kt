package com.factory.appsfactory.challenge.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.factory.appsfactory.challenge.presentation.extensions.showSnackBar
import com.factory.appsfactory.challenge.presentation.viewmodel.BaseViewModel
import com.factory.appsfactory.challenge.ui.utils.dismissLoadingDialog
import com.factory.appsfactory.challenge.ui.utils.showLoadingDialog

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
        if (isLoading) showLoadingDialog() else dismissLoadingDialog()
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLoadingDialog()
        showSnackBar(binding.root, message)
    }
}
