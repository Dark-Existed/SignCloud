package com.de.signcloud.repository.remote

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.liveData
import com.de.signcloud.api.SignCloudNetwork
import com.de.signcloud.bean.ValidateCode
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

sealed class User {
    @Immutable
    data class LoggedInUser(val phone: String) : User()
    object NoUserLoggedIn : User()
}


object UserRepository {

    private var _user: User = User.NoUserLoggedIn

    // TODO: 2021/4/6 is local user exist ?
    val user: User
        get() = _user


    fun signIn(phone: String, password: String) {
        _user = User.LoggedInUser(phone)
    }

    fun signInWithValidateCode(phone: String, validateCode: String) {

    }

    fun signUp(phone: String, password: String) {
        // TODO: 2021/4/6 random user name
        _user = User.LoggedInUser(phone)
    }

    fun getValidate(phone: String) = liveData(Dispatchers.IO) {
        val result = try {
            val validateCodeResponse: ValidateCode = SignCloudNetwork.getValidateCode(phone)
            if (validateCodeResponse.code == 200) {
                Result.success(validateCodeResponse)
            } else {
                Result.failure(RuntimeException("respnse status is ${validateCodeResponse.code}"))
            }
        } catch (e:Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun isKnownUserPhone(phone: String): Boolean {
        // if the phone contains "sign up" we consider it unknown
        return !phone.contains("1360")
    }
}