package com.de.signcloud.repository.local

import android.content.Context
import android.util.Log
import com.de.signcloud.SignCloudApplication
import com.de.signcloud.SignCloudApplication.Companion.context
import com.de.signcloud.utils.UserInfoDataStoreKey
import com.de.signcloud.utils.userInfoDataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object UserDao {

    fun signIn() {
        val editor = context.getSharedPreferences("user_info", Context.MODE_PRIVATE).edit()
        editor.putBoolean("isSignIn", true)
        editor.apply()
    }

    fun isUserSignIn(): Boolean {
        val pref = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        return pref.getBoolean("isSignIn", false)
    }


}