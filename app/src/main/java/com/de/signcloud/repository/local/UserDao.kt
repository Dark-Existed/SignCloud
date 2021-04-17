package com.de.signcloud.repository.local

import android.util.Log
import com.de.signcloud.SignCloudApplication
import com.de.signcloud.SignCloudApplication.Companion.context
import com.de.signcloud.utils.UserInfoDataStoreKey
import com.de.signcloud.utils.userInfoDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object UserDao {

    suspend fun isUserSignIn() = withContext(Dispatchers.IO) {
        val userName = context.userInfoDataStore.data.map {
            it[UserInfoDataStoreKey.userNameKey]
        }.first()
        if (userName != null) {
            return@withContext true
        } else {
            false
        }
    }


}