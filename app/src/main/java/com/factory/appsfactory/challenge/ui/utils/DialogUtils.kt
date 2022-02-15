package com.factory.appsfactory.challenge.ui.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.factory.appsfactory.challenge.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

var loadingDialog: Dialog? = null

fun Fragment.showLoadingDialog(
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    return MaterialAlertDialogBuilder(context ?: return null).apply {
        setView(R.layout.partial_loading_dialog)
    }.create().let { dialog ->
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                dialog.dismiss()
                if (loadingDialog === dialog) {
                    loadingDialog = null
                }
            }
        })
        loadingDialog = dialog
        dialog.show()
        dialog
    }
}

fun dismissLoadingDialog() {
    if (loadingDialog?.isShowing == true) {
        loadingDialog?.dismiss()
    }
}

