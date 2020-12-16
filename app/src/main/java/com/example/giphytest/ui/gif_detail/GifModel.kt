package com.example.giphytest.ui.gif_detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.giphytest.constant.Constants
import com.example.giphytest.data.model.GiphyData
import com.example.giphytest.ui.MainViewModel
import com.jio.media.jioguard.base.BaseViewModel
import kotlinx.coroutines.launch

class GifModel(application: Application) : BaseViewModel(application) {

    var adapter: GifGridAdapter = GifGridAdapter()
    val trendingGiphyResponse = MutableLiveData<GiphyData>()
    var previousRating = ""


    fun initAdapter(shareModel: MainViewModel) {
        adapter.viewModel = shareModel
    }

    fun requestTrendingGiphy(limit: Int = 25, rating: String = "g") {
        previousRating = rating
        val map = mapOf<String, Any>(
                Constants.REQUEST_API_KEY to Constants.API_KEY,
                Constants.LIMIT to limit,
                Constants.RATING to rating
        )

        viewModelScope.launch {
            safeCoroutineCall({ dataManager.getTrendingGiphy(map) }) { apiData, _ ->
                trendingGiphyResponse.value = apiData
            }
        }
    }
}