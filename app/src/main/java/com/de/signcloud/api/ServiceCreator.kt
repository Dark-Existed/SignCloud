package com.de.signcloud.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "http://59.77.134.88:8080"

    const val SIGN_IN_WITH_GITHUB_URL =
        "https://github.com/login/oauth/authorize?client_id=f733d49acafb50cac307&state=STATE&redirect_uri=$BASE_URL/api/callback"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}