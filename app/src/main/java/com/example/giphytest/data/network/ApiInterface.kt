package com.jio.media.jioguard.data.network

import com.example.giphytest.data.model.GiphyData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("/v1/gifs/trending")
    suspend fun getTrendingGiphy(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Response<GiphyData>

}