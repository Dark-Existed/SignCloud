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

    @GET("/api/users/phone-exist")
    fun isPhoneExist(
        @Query("phone") phone: String
    ): Call<IsPhoneExistResponse>

    @POST("/api/users/register/mobile")
    fun signUp(
        @Query("username") userName: String,
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("verificationCode") validateCode: String
    ): Call<SignInResponse>

    @POST("/api/users/login/passwords")
    fun signInWithPassword(
        @Query("phone") phone: String,
        @Query("password") password: String
    ): Call<SignInResponse>

    @POST("/api/users/login/codes")
    fun signInWithValidateCode(
        @Query("phone") phone: String,
        @Query("verificationCode") validateCode: String
    ): Call<SignInResponse>

    @GET("/api/github/callback")
    fun signInWithGithubCode(
        @Query("code") code: String,
        @Query("state") state: String = "STATE"
    ): Call<SignInResponse>

    @PUT("/api/github/bind-users")
    fun bindPhone(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("userId") githubId: Int,
        @Query("verificationCode") validateCode: String,
    ): Call<SignInResponse>

    @GET("/api/users/getCodes")
    fun getValidateCode(
        @Query("phone") phone: String
    ): Call<ValidateCodeResponse>

    @POST("/api/users/forgetPasswords")
    fun resetPassword(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("verificationCode") validateCode: String
    ):Call<ResetPasswordResponse>

}