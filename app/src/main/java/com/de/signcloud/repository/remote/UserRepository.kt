package com.de.signcloud.repository.remote

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.liveData
import com.de.signcloud.SignCloudApplication.Companion.context
import com.de.signcloud.api.SignCloudNetwork
import com.de.signcloud.bean.ResetPasswordResponse
import com.de.signcloud.bean.SignInResponse
import com.de.signcloud.bean.ValidateCodeResponse
import com.de.signcloud.repository.local.UserDao
import com.de.signcloud.utils.UserInfoDataStoreKey
import com.de.signcloud.utils.userInfoDataStore
import com.de.signcloud.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext


class User(
    val id: Int,
    val name: String,
    val phone: String,
    val avatar: String,
    val defaultRole: String,
    val token: String
)

object UserRepository {

    private lateinit var _user: User

    val user: User
        get() = _user

    fun isUserStudent(): Boolean {
        return _user.defaultRole == "student"
    }

    fun readUser() {
        runBlocking {
            context.userInfoDataStore.data.first {
                val userId = it[UserInfoDataStoreKey.userIdKey] ?: -1
                val userName = it[UserInfoDataStoreKey.userNameKey] ?: ""
                val phone = it[UserInfoDataStoreKey.phoneKey] ?: ""
                val avatar = it[UserInfoDataStoreKey.avatar] ?: ""
                val defaultRole = it[UserInfoDataStoreKey.defaultRoleKey] ?: ""
                val token = it[UserInfoDataStoreKey.tokenKey] ?: ""
                _user = User(userId, userName, phone, avatar, defaultRole, token)
                true
            }
        }
    }

    fun isPhoneExist(phone: String) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.isPhoneExist(phone)
        if (result.code == 200) {
            Result.Success(result.data)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }


    fun signInWithPassword(phone: String, password: String) = request(Dispatchers.IO) {
        val signInResponse: SignInResponse =
            SignCloudNetwork.signInWithPassword(phone, password)
        when (signInResponse.code) {
            200 -> {
                updateUserInfo(signInResponse)
                Result.Success(signInResponse)
            }
            400 -> Result.Success(signInResponse)
            else -> Result.Failure(RuntimeException("sign in response status is ${signInResponse.code}"))
        }
    }


    fun signInWithValidateCode(phone: String, validateCode: String) = request(Dispatchers.IO) {
        val signInResponse: SignInResponse =
            SignCloudNetwork.signInWithValidateCode(phone, validateCode)
        when (signInResponse.code) {
            200 -> {
                updateUserInfo(signInResponse)
                Result.Success(signInResponse)
            }
            400 -> Result.Success(signInResponse)
            else -> Result.Failure(RuntimeException("sign in response status is ${signInResponse.code}"))
        }
    }

    fun signInWithGithubCode(code: String) = request(Dispatchers.IO) {
        val signInResponse: SignInResponse = SignCloudNetwork.signInWithGithubCode(code)
        when (signInResponse.code) {
            200 -> {
                updateUserInfo(signInResponse)
                Result.Success(signInResponse)
            }
//            406 -> Result.Success(signInResponse)
            else -> Result.Failure(RuntimeException("sign in response status is ${signInResponse.code}"))
        }
    }


    fun bindPhone(phone: String, password: String, validateCode: String, githubId: Int) =
        request(Dispatchers.IO) {
            val bindResponse: SignInResponse =
                SignCloudNetwork.bindPhone(phone, password, githubId, validateCode)
            Log.d("UserRepository", bindResponse.code.toString())
            when (bindResponse.code) {
                200 -> {
                    updateUserInfo(bindResponse)
                    Result.Success(bindResponse)
                }
                407, 409 -> {
                    Result.Success(bindResponse)
                }
                else -> Result.Failure(RuntimeException("bind phone response status is ${bindResponse.code}"))
            }
        }

    fun signUp(phone: String, password: String, validateCode: String) = request(Dispatchers.IO) {
        val signUpResponse: SignInResponse =
            SignCloudNetwork.signUp(phone, password, validateCode)
        when (signUpResponse.code) {
            200 -> {
//                _user = User.LoggedInUser(phone)
                updateUserInfo(signUpResponse)
                Result.Success(signUpResponse)
            }
            400 -> Result.Success(signUpResponse)
            else -> Result.Failure(RuntimeException("sign in response status is ${signUpResponse.code}"))
        }
    }

    fun resetPassword(phone: String, password: String, validateCode: String) =
        request(Dispatchers.IO) {
            val resetPasswordResponse: ResetPasswordResponse =
                SignCloudNetwork.resetPassword(phone, password, validateCode)
            if (resetPasswordResponse.code == 200) {
                Result.Success(resetPasswordResponse)
            } else {
                Result.Failure(RuntimeException("reset password response status is ${resetPasswordResponse.code}"))
            }
        }

    fun getValidate(phone: String) = request(Dispatchers.IO) {
        val validateCodeResponse: ValidateCodeResponse = SignCloudNetwork.getValidateCode(phone)
        if (validateCodeResponse.code == 200) {
            Result.Success(validateCodeResponse)
        } else {
            Result.Failure(RuntimeException("response status is ${validateCodeResponse.code}"))
        }
    }


    private suspend fun updateUserInfo(signInResponse: SignInResponse) {
        val userId = signInResponse.data!!.userInfo!!.id
        val userName = signInResponse.data.userInfo!!.userName
        val phone = signInResponse.data.userInfo.phone
        val avatar = signInResponse.data.userInfo.avatar
        val defaultRole = signInResponse.data.userInfo.defaultRole
        val token = signInResponse.data.token!!
        context.userInfoDataStore.edit { userInfo ->
            userInfo[UserInfoDataStoreKey.userIdKey] = userId
            userInfo[UserInfoDataStoreKey.userNameKey] = userName
            userInfo[UserInfoDataStoreKey.phoneKey] = phone
            userInfo[UserInfoDataStoreKey.avatar] = avatar
            userInfo[UserInfoDataStoreKey.defaultRoleKey] = defaultRole
            userInfo[UserInfoDataStoreKey.tokenKey] = token
        }
        _user = User(userId, userName, phone, avatar, defaultRole, token)
        UserDao.signIn()
    }




}


private fun <T> request(context: CoroutineContext, block: suspend () -> Result<T>) =
    liveData(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(e)
        }
        emit(result)
    }