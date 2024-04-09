package com.example.imageloadlibraries

import android.util.Log
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val store: CookieJar) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val cookies = store.loadForRequest(chain.request().url)

        Log.e(this.toString(), "intercept: $cookies")

        cookies.find { it.name.contentEquals("ETag") }
        val tag = cookies.find { it.name.contentEquals("ETag") }
            ?.value

        val request = chain.request().newBuilder()
        tag?.let {
            request.addHeader("If-None-Match", "\"$it\"")
        }

        return chain.proceed(request.build())
    }
}