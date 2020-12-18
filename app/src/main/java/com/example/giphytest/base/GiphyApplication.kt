package com.example.giphytest.base

import android.app.Application
import com.example.giphytest.constant.AppSingleton
import com.jio.media.jioguard.data.network.ApiManager

class GiphyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        AppSingleton.application = this
    }
}