package com.de.signcloud.bean

import com.google.gson.annotations.SerializedName

class ChangeRoleResponse(val code: Int, val message: String, val data: String)

class SetUserInfoResponse(val code: Int, val message: String, val data: String)

class SetUserAvatarResponse(
    val code: Int,
    val message: String,
    @SerializedName("data") val imageUrl: String
)