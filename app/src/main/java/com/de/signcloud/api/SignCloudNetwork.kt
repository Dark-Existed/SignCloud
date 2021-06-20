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

    suspend fun signInWithGithubCode(code: String) =
        signInSignUpService.signInWithGithubCode(code).await()

    suspend fun bindPhone(phone: String, password: String, githubId: Int, validateCode: String) =
        signInSignUpService.bindPhone(phone, password, githubId, validateCode).await()

    suspend fun resetPassword(phone: String, password: String, validateCode: String) =
        signInSignUpService.resetPassword(phone, password, validateCode).await()


    private val courseService = ServiceCreator.createWithToken(CourseService::class.java)

    suspend fun getSchools(pageNum: Int = 0, pageSize: Int = 999) =
        courseService.getSchools(pageNum, pageSize).await()

    suspend fun createCourse(
        teacherName: String,
        courseName: String,
        className: String,
        schoolName: String,
        collegeName: String,
        grade: String,
        semester: String,
        courseRequirement: String?,
        examArrangement: String?,
        classSchedule: String?,
    ) = courseService.createCourse(
        teacherName,
        schoolName,
        className,
        collegeName,
        grade,
        semester,
        courseName,
        courseRequirement,
        examArrangement,
        classSchedule,
//        null
    ).await()

    suspend fun getCourseCreate(pageNum: Int = 0, pageSize: Int = 999) =
        courseService.getCoursesCreate(pageNum, pageSize).await()

    suspend fun getCourseByCode(code: String) = courseService.getCourseByCode(code).await()

    suspend fun joinCourse(code: String) = courseService.joinCourse(code).await()

    suspend fun getJoinedCourse() = courseService.getCoursesJoined().await()


    private val userSettingService = ServiceCreator.createWithToken(UserSettingService::class.java)

    suspend fun changeRole(role: String) = userSettingService.setUserDefaultRole(role).await()


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