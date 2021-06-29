package com.de.signcloud.repository.remote

import androidx.lifecycle.liveData
import com.de.signcloud.api.SignCloudNetwork
import com.de.signcloud.ui.course.TextState
import com.de.signcloud.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


object CourseRepository {

    fun getSchoolsSuggestions() = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getSchools()
        if (result.code == 200) {
            Result.Success(result.data)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun createCourse(textState: TextState) = request(Dispatchers.IO) {
        val splitList = textState.schoolSelectState.text.split("-")
        val schoolName = splitList[0]
        val collegeName = splitList[1]
        val result = SignCloudNetwork.createCourse(
            UserRepository.user.name,
            textState.courseNameState.text,
            textState.classNameState.text,
            schoolName,
            collegeName,
            textState.gradeSelectedState.text,
            textState.semesterSelectedState.text,
            textState.courseRequirementsState.text,
            textState.examArrangementState.text,
            textState.classScheduleState.text
        )
        if (result.code == 200) {
            Result.Success(result)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun getCourseCreate() = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getCourseCreate()
        if (result.code == 200) {
            Result.Success(result.data)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun getCourseByCode(code: String) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getCourseByCode(code)
        when (result.code) {
            200, 400 -> Result.Success(result)
            else -> Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun joinCourse(code: String) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.joinCourse(code)
        if (result.code == 200) {
            Result.Success(true)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun getCourseJoined() = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getJoinedCourse()
        if (result.code == 200) {
            Result.Success(result.courses)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
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