package com.timurkhabibulin.testandroid.data

import com.timurkhabibulin.testandroid.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val CONTENT_TYPE_HEADER = "Content-Type"
private const val APPLICATION_JSON = "application/json"
private const val ACCEPT_VERSION_HEADER = "Accept-Version"
private const val ACCEPT_VERSION = "v1"
private const val AUTHORIZATION_HEADER = "Authorization"

internal class AuthorizationInterceptor : Interceptor {

    private val accessToken: String = BuildConfig.UNSPLASH_ACCESS_KEY
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .addHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
            .addHeader(ACCEPT_VERSION_HEADER, ACCEPT_VERSION)
            .addHeader(AUTHORIZATION_HEADER, "Client-ID $accessToken")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}
