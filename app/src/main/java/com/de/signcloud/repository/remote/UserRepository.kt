package com.de.signcloud.repository.remote

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.liveData
import com.de.signcloud.SignCloudApplication.Companion.context
import com.de.signcloud.api.SignCloudNetwork
import com.de.signcloud.bean.SignInResult
import com.de.signcloud.bean.SignUpResult
import com.de.signcloud.bean.ValidateCodeResult
import com.de.signcloud.utils.UserInfoDataStoreKey
import com.de.signcloud.utils.userInfoDataStore
import kotlinx.coroutines.Dispatchers


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

//    fun signInWithPassword(phone: String, password: String) {
//        _user = User.LoggedInUser(phone)
//    }

    fun signInWithPassword(phone: String, password: String) = liveData(Dispatchers.IO) {
        val result = try {
            Log.d("UserRepository", "Log In With Password")
            val signInResult: SignInResult = SignCloudNetwork.signInWithPassword(phone, password)
            if (signInResult.code == 200) {
                updateUserInfo(signInResult)
                Result.success(signInResult)
            } else {
                Result.failure(RuntimeException("sign in response status is ${signInResult.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun signInWithValidateCode(phone: String, validateCode: String) = liveData(Dispatchers.IO) {
        val result = try {
            val signInResult: SignInResult =
                SignCloudNetwork.signInWithValidateCode(phone, validateCode)
            if (signInResult.code == 200) {
                updateUserInfo(signInResult)
                Result.success(signInResult)
            } else {
                Result.failure(RuntimeException("sign in response status is ${signInResult.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun signUp(phone: String, password: String, validateCode: String) = liveData(Dispatchers.IO) {
        val result = try {
            val signUpResult: SignUpResult = SignCloudNetwork.signUp(phone, password, validateCode)
            if (signUpResult.code == 200) {
//                _user = User.LoggedInUser(phone)
                Result.success(signUpResult)
            } else {
                Result.failure(RuntimeException("sign up response status is ${signUpResult.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getValidate(phone: String) = liveData(Dispatchers.IO) {
        val result = try {
            val validateCodeResponse: ValidateCodeResult = SignCloudNetwork.getValidateCode(phone)
            if (validateCodeResponse.code == 200) {
                Log.d("UserRepository", validateCodeResponse.data)
                Result.success(validateCodeResponse)
            } else {
                Result.failure(RuntimeException("response status is ${validateCodeResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun isKnownUserPhone(phone: String): Boolean {
        // if the phone contains "sign up" we consider it unknown
        return !phone.contains("1360")
    }


    suspend fun updateUserInfo(signInResult: SignInResult) {
        context.userInfoDataStore.edit { userInfo ->
            userInfo[UserInfoDataStoreKey.userNameKey] = signInResult.data!!.userInfo.userName
            userInfo[UserInfoDataStoreKey.phoneKey] = signInResult.data.userInfo.phone
            userInfo[UserInfoDataStoreKey.defaultRoleKey] = signInResult.data.userInfo.defaultRole
            userInfo[UserInfoDataStoreKey.tokenKey] = signInResult.data.token
        }
    }

}