package com.de.signcloud.repository.remote

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.liveData
import com.de.signcloud.SignCloudApplication.Companion.context
import com.de.signcloud.api.SignCloudNetwork
import com.de.signcloud.bean.SignInResponse
import com.de.signcloud.bean.SignUpResponse
import com.de.signcloud.bean.ValidateCodeResponse
import com.de.signcloud.utils.UserInfoDataStoreKey
import com.de.signcloud.utils.userInfoDataStore
import com.de.signcloud.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


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
            val signInResponse: SignInResponse =
                SignCloudNetwork.signInWithPassword(phone, password)
            if (signInResponse.code == 200) {
                updateUserInfo(signInResponse)
                Result.Success(signInResponse)
            } else {
                Result.Failure(RuntimeException("sign in response status is ${signInResponse.code}"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
        emit(result)
    }


    fun signInWithValidateCode(phone: String, validateCode: String) = liveData(Dispatchers.IO) {
        val result = try {
            val signInResponse: SignInResponse =
                SignCloudNetwork.signInWithValidateCode(phone, validateCode)
            if (signInResponse.code == 200) {
                updateUserInfo(signInResponse)
                Result.Success(signInResponse)
            } else {
                Result.Failure(RuntimeException("sign in response status is ${signInResponse.code}"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
        emit(result)
    }

    fun signUp(phone: String, password: String, validateCode: String) = liveData(Dispatchers.IO) {
        val result = try {
            val signUpResponse: SignUpResponse =
                SignCloudNetwork.signUp(phone, password, validateCode)
            if (signUpResponse.code == 200) {
//                _user = User.LoggedInUser(phone)
                Result.Success(signUpResponse)
            } else {
                Result.Failure(RuntimeException("sign up response status is ${signUpResponse.code}"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
        emit(result)
    }

    fun getValidate(phone: String) = liveData(Dispatchers.IO) {
        val result = try {
            val validateCodeResponse: ValidateCodeResponse = SignCloudNetwork.getValidateCode(phone)
            if (validateCodeResponse.code == 200) {
                Result.Success(validateCodeResponse)
            } else {
                Result.Failure(RuntimeException("response status is ${validateCodeResponse.code}"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
        emit(result)
    }

    fun isKnownUserPhone(phone: String): Boolean {
        // if the phone contains "sign up" we consider it unknown
        return !phone.contains("1360")
    }


    suspend fun updateUserInfo(signInResponse: SignInResponse) {
        context.userInfoDataStore.edit { userInfo ->
            userInfo[UserInfoDataStoreKey.userNameKey] = signInResponse.data!!.userInfo.userName
            userInfo[UserInfoDataStoreKey.phoneKey] = signInResponse.data.userInfo.phone
            userInfo[UserInfoDataStoreKey.defaultRoleKey] = signInResponse.data.userInfo.defaultRole
            userInfo[UserInfoDataStoreKey.tokenKey] = signInResponse.data.token
        }
    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.Failure(e)
            }
            emit(result)
        }


}