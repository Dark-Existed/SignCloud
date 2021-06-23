package com.de.signcloud.api

import com.de.signcloud.bean.CreateCheckInResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import java.math.BigDecimal

interface CheckInService {

    @POST("/api/signIn")
    fun createCheckIn(
        @Query("code") code: String,
        @Query("mode") mode: String,
        @Query("value") minutes: Int,
        @Query("latitude") latitude: BigDecimal,
        @Query("longitude") longitude: BigDecimal
    ): Call<CreateCheckInResponse>

    @PUT("/api/signIn/end")
    fun finishCheckIn(
        @Query("courseSignInId") checkInId: Int
    )

    @GET("/api/signIn/students")
    fun getCheckInList(
        @Query("code") code: String
    )

    fun getCheckIn(
        @Query("code") code: String
    )

    @POST("/api/signIn/students")
    fun checkIn(

    )

}