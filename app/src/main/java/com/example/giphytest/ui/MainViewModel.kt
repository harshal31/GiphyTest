package com.example.giphytest.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.giphytest.data.model.DataItem
import com.jio.media.jioguard.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    val gifDetailUrl = MutableLiveData("")
    var menuRatingClicked = MutableLiveData("")

    fun onGifItemClick(data: String) {
        gifDetailUrl.value = data
    }
}