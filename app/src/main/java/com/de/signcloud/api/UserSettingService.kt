package com.de.signcloud.api

import com.de.signcloud.bean.ChangeRoleResponse
import retrofit2.Call
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserSettingService {

    @PUT("/api/roles/defaults")
    fun setUserDefaultRole(
        @Query("role") role: String
    ): Call<ChangeRoleResponse>

}