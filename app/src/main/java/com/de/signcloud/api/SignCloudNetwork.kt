package com.de.signcloud.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import java.math.BigDecimal
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

    suspend fun deleteCourse(code: String) = courseService.deleteCourse(code).await()

    suspend fun getCourseCreate(pageNum: Int = 0, pageSize: Int = 999) =
        courseService.getCoursesCreate(pageNum, pageSize).await()

    suspend fun getCourseByCode(code: String) = courseService.getCourseByCode(code).await()

    suspend fun joinCourse(code: String) = courseService.joinCourse(code).await()

    suspend fun getJoinedCourse() = courseService.getCoursesJoined().await()


    private val userSettingService = ServiceCreator.createWithToken(UserSettingService::class.java)

    suspend fun changeRole(role: String) = userSettingService.setUserDefaultRole(role).await()

    suspend fun setUserInfo(ino: String, userName: String) =
        userSettingService.setUserInfo(ino, userName).await()


    private val checkInService = ServiceCreator.createWithToken(CheckInService::class.java)

    suspend fun createCheckIn(
        code: String,
        mode: String,
        minutes: Int,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) = checkInService.createCheckIn(code, mode, minutes, latitude, longitude).await()

    suspend fun getCheckInList(code: String, pageNum: Int = 0, pageSize: Int = 999) =
        checkInService.getCourseCheckInList(code, pageNum, pageSize).await()

    suspend fun getCurrentCheckIn(code: String) = checkInService.getCurrentCheckIn(code).await()

    suspend fun checkIn(
        checkInId: Int,
        latitude: BigDecimal,
        longitude: BigDecimal,
        distance: Double
    ) = checkInService.checkIn(checkInId, latitude, longitude, distance).await()

    suspend fun getStudentCheckInStatus(checkInId: Int) =
        checkInService.getStudentCheckInStatus(checkInId).await()

    suspend fun getCourseStudents(code: String) = checkInService.getCourseStudents(code).await()

    suspend fun finishCheckIn(checkInId: Int) = checkInService.finishCheckIn(checkInId).await()

    suspend fun getStudentCheckInHistory(code: String, userId: Int) =
        checkInService.getCheckStudentInHistory(code, userId).await()

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