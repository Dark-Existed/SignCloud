package com.de.signcloud.api

import com.de.signcloud.bean.ChangeRoleResponse
import com.de.signcloud.bean.SetUserInfoResponse
import retrofit2.Call
import retrofit2.http.PUT
import retrofit2.http.Query

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

}