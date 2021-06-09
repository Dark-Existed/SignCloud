package com.de.signcloud.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.userInfoDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")

object UserInfoDataStoreKey {
    val userIdKey = intPreferencesKey("userId")
    val userNameKey = stringPreferencesKey("userName")
    val phoneKey = stringPreferencesKey("phone")
    val avatar = stringPreferencesKey("avatar")
    val defaultRoleKey = stringPreferencesKey("defaultRole")
    val tokenKey = stringPreferencesKey("token")
}