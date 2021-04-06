package com.de.signcloud.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignInSignUpService {

    @POST("/api/mobileRegister")
    suspend fun signUp(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("verificationCode") validateCode: String
    )

    @POST("/api/login")
    suspend fun signInWithPassword(
        @Query("userLogin") userLogin: String
    )

    @GET("/api/getCode")
    suspend fun signInWithValidateCode(
        @Query("phone") phone: String
    )

    @POST("/api/forgetPassword")
    suspend fun resetPassword(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("verificationCode") validateCode: String
    )

}