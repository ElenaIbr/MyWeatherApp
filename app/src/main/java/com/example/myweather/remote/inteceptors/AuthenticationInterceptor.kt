package com.example.myweather.remote.inteceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("appid", "cd8eb75dc9af795cbeb5e149d4bc0438").build()
        requestBuilder.url(url);
        return chain.proceed(requestBuilder.build())
    }
}
