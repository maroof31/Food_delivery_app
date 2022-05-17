package com.nevermindapp.newproject1.utils

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat


class ViewUtils {

    fun Context.ShowToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
    }


}