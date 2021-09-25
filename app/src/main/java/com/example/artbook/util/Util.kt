package com.example.artbook.util

import com.example.artbook.BuildConfig
import timber.log.Timber

object Util {

    @JvmStatic
    fun logResponse(msg: String) {
        if (BuildConfig.DEBUG) {
            Timber.e(msg)
        }
    }

}