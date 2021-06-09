package com.de.signcloud.api

import com.de.signcloud.utils.RequestHeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "http://59.77.134.88:8080"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    private val clientWithToken = OkHttpClient.Builder()
        .addNetworkInterceptor(RequestHeaderInterceptor())
        .build()

    private val retrofitWithToken = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(clientWithToken)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createWithToken(serviceClass: Class<T>): T = retrofitWithToken.create(serviceClass)
}