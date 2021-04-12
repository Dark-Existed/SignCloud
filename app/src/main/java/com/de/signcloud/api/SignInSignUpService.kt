package com.de.signcloud.api

import com.de.signcloud.bean.SignInResult
import com.de.signcloud.bean.SignUpResult
import com.de.signcloud.bean.ValidateCodeResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignInSignUpService {

    @POST("/api/mobileRegister")
    fun signUp(
        @Query("username") userName: String,
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("verificationCode") validateCode: String
    ): Call<SignUpResult>

    @POST("/api/loginByPwd")
    fun signInWithPassword(
        @Query("phone") phone: String,
        @Query("password") password: String
    ): Call<SignInResult>

    @POST("/api/loginByVerificationCode")
    fun signInWithValidateCode(
        @Query("phone") phone: String,
        @Query("verificationCode") validateCode: String
    ): Call<SignInResult>

    @GET("/api/getCode")
    fun getValidateCode(
        @Query("phone") phone: String
    ): Call<ValidateCodeResult>

    @POST("/api/forgetPassword")
    fun resetPassword(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("verificationCode") validateCode: String
    )

}