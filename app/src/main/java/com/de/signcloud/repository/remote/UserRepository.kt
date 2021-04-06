package com.de.signcloud.repository.remote

import androidx.compose.runtime.Immutable

sealed class User {
    @Immutable
    data class LoggedInUser(val phone: String) : User()
    object NoUserLoggedIn : User()
}


object UserRepository {

    private var _user: User = User.NoUserLoggedIn
    val user: User
        get() = _user


    fun signIn(phone: String, password: String) {
        _user = User.LoggedInUser(phone)
    }


    fun signUp(phone: String, password: String) {
        _user = User.LoggedInUser(phone)
    }

    fun isKnownUserPhone(phone: String): Boolean {
        // if the phone contains "sign up" we consider it unknown
        return !phone.contains("17")
    }
}