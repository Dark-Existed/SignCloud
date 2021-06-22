package com.de.signcloud.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SchoolResponse(val code: Int, val message: String, val data: Data) {
    class Data(
        @SerializedName("content") val schools: List<School>,
    ) {
        class School(
            val id: Int,
            val name: String,
            val hasChildren: Boolean,
            @SerializedName("children") val colleges: List<College>
        )

        class College(
            val id: Int,
            val name: String,
            val parentId: Int,
        )
    }
}


class CreateCourseResponse(val code: Int, val message: String, val data: Data?) {
    class Data(val code: String, @SerializedName("imgUrl") val imageUrl: String)
}

class GetCoursesCreateResponse(val code: Int, val message: String, val data: Data?) {
    class Data(@SerializedName("content") val courses: List<Course>)
}

class GetCourseByCodeResponse(
    val code: Int,
    val message: String,
    @SerializedName("data") val course: Course
)

class JoinCourseResponse(val code: Int, val message: String)

class GetJoinedCourseResponse(
    val code: Int,
    val message: String,
    @SerializedName("data") val courses: List<Course>
)

class Course(
    val id: Int,
    val name: String,
    val className: String,
    val code: String,
    val grade: String,
    val semester: String,
    val school: String,
    val college: String,
    val teacher: String,
    @SerializedName("learnRequire") val courseRequirement: String,
    @SerializedName("teachProgress") val classSchedule: String,
    @SerializedName("examArrange") val examArrangement: String,
    val cover: String,
    @SerializedName("qrcode") val qrCode: String,
) : Serializable