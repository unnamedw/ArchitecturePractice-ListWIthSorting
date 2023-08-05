package com.doachgosum.marketsampleapp.presentation.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.doachgosum.marketsampleapp.MyApplication
import com.doachgosum.marketsampleapp.di.AppContainer

fun Activity.getAppContainer(): AppContainer {
    return (application as MyApplication).appContainer
}

fun Fragment.getAppContainer(): AppContainer {
    return requireActivity().getAppContainer()
}

private var toast: Toast? = null
fun Context.showToast(msg: String) {
    toast?.cancel()
    toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    toast?.show()
}