package com.example.giphytest.ui.gif_detail

import android.app.Application
import com.jio.media.jioguard.base.BaseViewModel

class GifDetailViewModel(application: Application) : BaseViewModel(application) {
    init {
        isDisplayBackButton.value = true
    }
}