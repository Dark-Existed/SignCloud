package com.de.signcloud.utils

import okhttp3.Interceptor
import okhttp3.Response

class ApiTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = "get token"
        return chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("token", token)
                    .build()
            )
        }
    }
}