package com.factory.appsfactory.challenge.presentation.extensions

import android.view.View
import androidx.fragment.app.Fragment
import com.factory.appsfactory.challenge.R
import com.google.android.material.snackbar.Snackbar

internal fun Fragment.showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        anchorView = view.rootView.findViewById(R.id.helper_view)
        show()
    }
}
