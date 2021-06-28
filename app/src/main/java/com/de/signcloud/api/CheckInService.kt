package com.de.signcloud.api

import com.de.signcloud.bean.*
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

    @GET("/api/signIn")
    fun getCourseCheckInList(
        @Query("code") code: String,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<GetCheckInList>

    @GET("/api/classes/students")
    fun getCourseStudents(
        @Query("code") code: String
    ): Call<GetCourseStudentResponse>

    @PUT("/api/signIn/end")
    fun finishCheckIn(
        @Query("courseSignInId") checkInId: Int
    ): Call<FinishCheckInResponse>

    @GET("/api/signIn/students/courses")
    fun getStudentCheckInStatus(
        @Query("courseSignInId") checkInId: Int
    ): Call<StudentCheckInStatusResponse>

    @GET("/api/signIn/courses")
    fun getCurrentCheckIn(
        @Query("code") code: String
    ): Call<CurrentCheckInResponse>

    @POST("/api/signIn/students")
    fun checkIn(
        @Query("courseSignId") checkInId: Int,
        @Query("latitude") latitude: BigDecimal,
        @Query("longitude") longitude: BigDecimal,
        @Query("distance") distance: Double
    ): Call<CheckInResponse>

}