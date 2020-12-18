package com.example.giphytest.ui

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.giphytest.data.model.DataItem
import com.example.giphytest.data.model.GiphyData
import com.jio.media.jioguard.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    val gifDetailData = MutableLiveData<DataItem>()
    val menuRatingClicked = MutableLiveData("")
    val gifItemClick = MutableLiveData<Pair<View, DataItem>>()


}