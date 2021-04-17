package com.de.signcloud.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SignCloudNetwork {

    private val signInSignUpService = ServiceCreator.create(SignInSignUpService::class.java)

    suspend fun isPhoneExist(phone: String) = signInSignUpService.isPhoneExist(phone).await()

    suspend fun signUp(phone: String, password: String, validateCode: String) =
        signInSignUpService.signUp(phone, phone, password, validateCode).await()

    suspend fun getValidateCode(phone: String) =
        signInSignUpService.getValidateCode(phone).await()

    suspend fun signInWithPassword(phone: String, password: String) =
        signInSignUpService.signInWithPassword(phone, password).await()

    suspend fun signInWithValidateCode(phone: String, validateCode: String) =
        signInSignUpService.signInWithValidateCode(phone, validateCode).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}