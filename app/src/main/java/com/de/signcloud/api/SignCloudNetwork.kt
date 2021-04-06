package com.de.signcloud.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SignCloudNetwork {

    private val signInSignUpService = ServiceCreator.create<SignInSignUpService>()

    suspend fun signUp(phone: String, password: String, validateCode: String) =
        signInSignUpService.signUp(phone, password, validateCode)

    suspend fun getValidateCode(phone: String) =
        signInSignUpService.getValidateCode(phone).await()

    
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