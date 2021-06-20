package com.de.signcloud.repository.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.de.signcloud.SignCloudApplication.Companion.context
import com.de.signcloud.utils.UserInfoDataStoreKey
import com.de.signcloud.utils.userInfoDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

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

    fun signOut() {
        val editor = context.getSharedPreferences("user_info", Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

    fun getCurRole(): String {
        var result = ""
        runBlocking {
            context.userInfoDataStore.data.first {
                result = it[UserInfoDataStoreKey.defaultRoleKey] ?: ""
                true
            }
        }
        return result
    }

    suspend fun changeDefaultRole(role: String) {
        context.userInfoDataStore.edit { userInfo ->
            userInfo[UserInfoDataStoreKey.defaultRoleKey] = role
        }
    }

}