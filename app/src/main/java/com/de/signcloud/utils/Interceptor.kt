package com.de.signcloud.utils

import com.de.signcloud.SignCloudApplication
import com.de.signcloud.repository.remote.UserRepository
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = UserRepository.user.token
        val response = chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
        }
        return if (response.code() != 403)
            response
        else {
            response
        }
    }
}