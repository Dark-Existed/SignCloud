package com.de.signcloud.api

import com.de.signcloud.bean.SchoolResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CourseService {

    @GET("/api/sys/schools")
    fun getSchools(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<SchoolResponse>

    @GET("/api/sys/schools/search")
    fun searchSchool(

    )

}