package com.kukareku.readonline.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snack(message: String, actionText: String? = null, action: (() -> Unit)? = null) {
    val sb = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    if (actionText != null && action != null) {
        sb.setAction(actionText) { action() }
    }
    sb.show()
}
