package com.example.mywallpaper.presentation.ext

import android.content.Context
import com.example.mywallpaper.R

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun Throwable.getErrorMessage(context: Context): String {
    return when (this) {
        is HttpException -> context.getString(R.string.error_unknown)
        is SocketTimeoutException -> context.getString(R.string.error_connection_timeout)
        is IOException -> context.getString(R.string.error_connection_not_found)
        else -> context.getString(R.string.error_unknown)
    }
}
