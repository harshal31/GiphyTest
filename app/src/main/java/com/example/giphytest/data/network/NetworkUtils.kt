package com.jio.media.jioguard.data.network

import android.content.Context
import android.net.ConnectivityManager


object NetworkUtils {


    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.isActiveNetworkMetered
    }

   /* @FlowPreview
    fun View.click(lifecycleOwner: LifecycleOwner, block: (View) -> Unit) = callbackFlow<View> {
        setOnClickListener {
            offer(it)
        }
        awaitClose { setOnClickListener(null) }
    }.debounce(500)
        .onEach { block(it) }
        .launchIn(lifecycleOwner.lifecycleScope)*/
}
