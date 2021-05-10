package com.de.signcloud.api

import retrofit2.http.GET

interface CourseService {

    @GET("/api/sys/schools")
    fun getSchools(

    )

    @GET("/api/sys/schools/children")
    fun getSchoolChildren(

    )

    @GET("/api/sys/schools/search")
    fun searchSchool(

    )

}