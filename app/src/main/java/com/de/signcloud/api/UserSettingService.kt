package com.de.signcloud.api

import com.de.signcloud.bean.ChangeRoleResponse
import com.de.signcloud.bean.SetUserAvatarResponse
import com.de.signcloud.bean.SetUserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

interface UserSettingService {

    @PUT("/api/roles/defaults")
    fun setUserDefaultRole(
        @Query("role") role: String
    ): Call<ChangeRoleResponse>

    @PUT("/api/admins/ino")
    fun setUserInfo(
        @Query("ino") ino: String,
        @Query("userName") userName: String
    ): Call<SetUserInfoResponse>

    @Multipart
    @PUT("/api/admins/avatar")
    fun setUserAvatar(
        @Part("file") file: MultipartBody.Part
    ): Call<SetUserAvatarResponse>

}