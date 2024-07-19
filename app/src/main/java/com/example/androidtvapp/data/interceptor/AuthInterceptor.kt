package com.example.androidtvapp.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            val url = chain.request().url.newBuilder().addQueryParameter(
                "apiKey", "90c806882637469f826b1ac8b2b3ec0b"
            ).build()
            url(url)
        }
        return chain.proceed(request.build())
    }

}