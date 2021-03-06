package com.de.signcloud.api

import com.de.signcloud.bean.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CourseService {

    @GET("/api/sys/schools")
    fun getSchools(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<SchoolResponse>

    @POST("/api/classes/courses")
    fun createCourse(
        @Query("teacher") teacherName: String,
        @Query("school") schoolName: String,
        @Query("className") className: String,
        @Query("college") collegeName: String,
        @Query("grade") grade: String,
        @Query("semester") semester: String,
        @Query("name") courseName: String,
        @Query("learnRequire") courseRequirements: String?,
        @Query("examArrange") examArrangement: String?,
        @Query("teachProgress") classSchedule: String?,
    ): Call<CreateCourseResponse>

    @DELETE("/api/classes/courses")
    fun deleteCourse(
        @Query("code") code: String
    ): Call<DeleteCourseResponse>

    @GET("/api/classes/courses/uid")
    fun getCoursesCreate(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<GetCoursesCreateResponse>

    @GET("/api/classes/courses/codes")
    fun getCourseByCode(
        @Query("code") code: String
    ): Call<GetCourseByCodeResponse>

    @POST("/api/classes/students/courses")
    fun joinCourse(
        @Query("code") code: String
    ): Call<JoinCourseResponse>

    @GET("/api/classes/students/courses")
    fun getCoursesJoined(): Call<GetJoinedCourseResponse>


}