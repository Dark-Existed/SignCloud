package com.de.signcloud.utils

import android.widget.Toast
import com.de.signcloud.R
import com.de.signcloud.SignCloudApplication
import com.de.signcloud.repository.remote.UserRepository
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = UserRepository.user.token
        return chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
        }
    }
}