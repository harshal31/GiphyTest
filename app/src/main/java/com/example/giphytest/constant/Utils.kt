package com.example.giphytest.constant

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.snackbar.Snackbar

fun View.snack(data: Any, length: Int = Snackbar.LENGTH_SHORT) {
    if (isShown && isVisible) {
        if (data is String) {
            Snackbar.make(this, data, length).show()
        } else if (data is Int) {
            Snackbar.make(this, data, length).show()
        }
    }
}


@GlideModule
class AppNameGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.apply {
            RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).signature(
                ObjectKey(System.currentTimeMillis().toShort())
            )
        }
    }
}



fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    val networkInfo = connectivityManager?.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}



