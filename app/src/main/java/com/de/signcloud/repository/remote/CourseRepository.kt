package com.de.signcloud.repository.remote

import android.util.Log
import androidx.lifecycle.liveData
import com.de.signcloud.api.CourseService
import com.de.signcloud.api.SignCloudNetwork
import com.de.signcloud.ui.createcourse.State
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

//    fun createCourse(state: State) = request(Dispatchers.IO) {
//        val result = SignCloudNetwork.createCourse()
//    }

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