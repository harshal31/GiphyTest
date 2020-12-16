package com.jio.media.jioguard.data.network

import com.example.giphytest.data.model.GiphyData
import retrofit2.Response


interface ApiHelper {
    suspend fun getTrendingGiphy(map: Map<String, Any>): Response<GiphyData>
}
