package com.example.imageloadlibraries

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.Response

class ETagInterceptor(private val store: CookieJar) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Save eTag from response header
        val eTag = response.headers.find { it.first.contentEquals("ETag") }
        eTag?.let { header ->
            val eTagCookie = Cookie.parse(request.url, "${header.first}=${header.second}")
            eTagCookie?.let {
                val cookies = mutableListOf<Cookie>()
                cookies.add(it)
                store.saveFromResponse(request.url, cookies)
            }
        }

        return response
    }
}