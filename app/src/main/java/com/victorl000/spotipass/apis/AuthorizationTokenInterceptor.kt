package com.victorl000.spotipass.apis

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationTokenInterceptor(private val context: Context) : Interceptor {
    private val TAG = this::class.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("authorization", "Bearer ${getAuthorizationToken(context)}")
            .build()

        return chain.proceed(request)
    }

    private fun getAuthorizationToken(context: Context): String {
        return TODO()
//        return runBlocking { DataStoreManager.getStringValue(context, ApplicationConstants.TOKEN) }
    }
}
