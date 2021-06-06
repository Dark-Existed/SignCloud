package com.de.signcloud.bean

import com.google.gson.annotations.SerializedName

class SchoolResponse(val code: Int, val message: String, val data: Data) {

    class Data(
        @SerializedName("content") val schools: List<School>,
        val pageable: Pageable,
        val totalElements: Int,
        val totalPages: Int,
        val last: Boolean,
        val number: Int,
        val size: Int,
        val sort: Pageable.Sort,
        val numberOfElements: Int,
        val first: Boolean,
        val empty: Boolean
    ) {

        class School(
            val id: Int,
            val name: String,
            val sort: Int,
            val parentId: Int,
            val hasChildren: Boolean,
            @SerializedName("children") val colleges: List<College>
        ) {
            class College(
                val id: Int,
                val name: String,
                val sort: Int,
                val parentId: Int,
                val hasChildren: Boolean,
                @SerializedName("children") val majors: List<Major>
            )

            class Major(
                val id: Int,
                val name: String,
                val sort: Int,
                val parentId: Int,
                val hasChildren: Boolean,
                val children: List<Any>
            )
        }

        class Pageable(
            val offset: Int,
            val pageNumber: Int,
            val pageSize: Int,
            @SerializedName("unpaged") val unPaged: Boolean,
            val sort: Sort
        ) {
            class Sort(val sorted: Boolean, val unsorted: Boolean, val empty: Boolean)
        }

    }

}


class SearchSchoolResponse() {

}

class CreateCourseResponse() {

}