package com.de.signcloud.api

import com.de.signcloud.bean.IsPhoneExistResponse
import com.de.signcloud.bean.ResetPasswordResponse
import com.de.signcloud.bean.SignInResponse
import com.de.signcloud.bean.ValidateCodeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SignInSignUpService {

    @GET("/api/phone-exist")
    fun isPhoneExist(
        @Query("phone") phone: String
    ): Call<IsPhoneExistResponse>

    @POST("/api/mobileRegister")
    fun signUp(
        @Query("username") userName: String,
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("verificationCode") validateCode: String
    ): Call<SignInResponse>

    @POST("/api/loginByPwd")
    fun signInWithPassword(
        @Query("phone") phone: String,
        @Query("password") password: String
    ): Call<SignInResponse>

    @POST("/api/loginByVerificationCode")
    fun signInWithValidateCode(
        @Query("phone") phone: String,
        @Query("verificationCode") validateCode: String
    ): Call<SignInResponse>

    @GET("api/callback")
    fun signInWithGithubCode(
        @Query("code") code: String,
        @Query("state") state: String = "STATE"
    ): Call<SignInResponse>

    @PUT("/api/password-phone")
    fun bindPhone(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("githubId") githubId: Int,
        @Query("verificationCode") validateCode: String,
    ): Call<SignInResponse>

    @GET("/api/getCode")
    fun getValidateCode(
        @Query("phone") phone: String
    ): Call<ValidateCodeResponse>

    @POST("/api/forgetPassword")
    fun resetPassword(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("verificationCode") validateCode: String
    ):Call<ResetPasswordResponse>

}