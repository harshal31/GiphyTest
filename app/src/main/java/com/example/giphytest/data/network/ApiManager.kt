package com.jio.media.jioguard.data.network

import android.app.Application
import com.example.giphytest.constant.AppSingleton
import com.example.giphytest.constant.Constants
import com.example.giphytest.constant.isConnected
import com.example.giphytest.data.model.GiphyData
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object ApiManager : ApiHelper {

    private val okHttpClient = OkHttpClient.Builder()
    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val gson = GsonBuilder().setPrettyPrinting().setLenient().serializeNulls().create()!!

    init {
        val cache = Cache(AppSingleton.application.cacheDir, (5 * 1024 * 1024).toLong())
        okHttpClient.cache(cache)
        okHttpClient.connectTimeout(240, TimeUnit.SECONDS)
        okHttpClient.readTimeout(240, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(240, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(httpLoggingInterceptor)
        okHttpClient.addInterceptor {
            var request = it.request()
            request = if (AppSingleton.application.isConnected()) {
                request.newBuilder().header(Constants.CACHE_CONTROL, Constants.PUBLIC_MAX_AGE + 5).build()
            } else {
                request.newBuilder().header(Constants.CACHE_CONTROL, Constants.PUBLIC_ONLY_CACHED + (60 * 60 * 24 * 7)).build()
            }
            it.proceed(request)
        }
    }

    private fun getApiInterface() = Retrofit.Builder()
        .baseUrl(Constants.DEV_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient.build())
        .build()
        .create(ApiInterface::class.java)

    override suspend fun getTrendingGiphy(map: Map<String, Any>): Response<GiphyData> =
        getApiInterface().getTrendingGiphy(map)


}