package com.de.signcloud.repository.local

import android.content.Context
import com.de.signcloud.SignCloudApplication.Companion.context

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


}