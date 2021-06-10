package com.de.signcloud.api

import com.de.signcloud.bean.CreateCourseResponse
import com.de.signcloud.bean.SchoolResponse
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
        @Query("college") collegeName: String,
        @Query("grade") grade: String,
        @Query("semester") semester: String,
        @Query("name") courseName: String,
        @Query("learnRequire") courseRequirements: String?,
        @Query("examArrange") examArrangement: String?,
        @Query("teachProgress") classSchedule: String?,
        @Part("cover") cover: MultipartBody.Part?,
    ): Call<CreateCourseResponse>

    @GET("/api/classes/courses/uid/{userId}")
    fun getCoursesCreate(
        @Path("userId") userId: Int,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    )

    @POST("/api/classes/students/courses")
    fun attendCourse()

    @GET("/api/classes/students/courses")
    fun getCoursesAttend()


}