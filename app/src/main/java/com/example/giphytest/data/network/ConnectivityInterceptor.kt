package com.jio.media.jioguard.data.network

import okhttp3.Interceptor
import okhttp3.Response


class ConnectivityInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

//            builder.addHeader(Constants.TOKEN_NAME, JioGatePreferences.guardToken)

        return chain.proceed(builder.build())
    }
}


