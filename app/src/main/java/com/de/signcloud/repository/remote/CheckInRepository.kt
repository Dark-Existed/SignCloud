package com.de.signcloud.repository.remote

import android.util.Log
import androidx.lifecycle.liveData
import com.de.signcloud.api.SignCloudNetwork
import com.de.signcloud.utils.Result
import kotlinx.coroutines.Dispatchers
import java.math.BigDecimal
import kotlin.coroutines.CoroutineContext

object CheckInRepository {

    fun createCheckIn(
        code: String,
        mode: String,
        minutes: Int,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.createCheckIn(code, mode, minutes, latitude, longitude)
        when (result.code) {
            200 -> Result.Success(result)
            400 -> Result.Success(result)
            else -> Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun getCheckInList(code: String) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getCheckInList(code)
        if (result.code == 200) {
            Result.Success(result.data.checkInList)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun getCurrentCheckIn(code: String) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getCurrentCheckIn(code)
        if (result.code == 200) {
            Result.Success(result)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun checkIn(
        checkInId: Int,
        latitude: BigDecimal,
        longitude: BigDecimal,
        distance: Double
    ) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.checkIn(checkInId, latitude, longitude, distance)
        if (result.code == 200) {
            Result.Success(result)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun getStudentCheckInStatus(checkInId: Int) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getStudentCheckInStatus(checkInId)
        if (result.code == 200) {
            Result.Success(result.data)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun getCourseStudents(code: String) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getCourseStudents(code)
        if (result.code == 200) {
            Result.Success(result.data)
        } else {
            Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun finishCheckIn(checkInId: Int) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.finishCheckIn(checkInId)
        when (result.code) {
            200, 400 -> Result.Success(result)
            else -> Result.Failure(RuntimeException("response status code is ${result.code}"))
        }
    }

    fun getStudentCheckInHistory(code: String, userId: Int) = request(Dispatchers.IO) {
        val result = SignCloudNetwork.getStudentCheckInHistory(code, userId)
        if (result.code == 200) {
            Result.Success(result.data)
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