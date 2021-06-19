package com.de.signcloud.api

import retrofit2.http.PUT
import retrofit2.http.Query

interface UserSettingService {

    @PUT
    fun setUserDefaultRole(
        @Query("role") role: String
    )

}