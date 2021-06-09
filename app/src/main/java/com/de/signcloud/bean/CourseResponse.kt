package com.de.signcloud.bean

import com.google.gson.annotations.SerializedName

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


class CreateCourseResponse(val code: Int, val message: String) {

}