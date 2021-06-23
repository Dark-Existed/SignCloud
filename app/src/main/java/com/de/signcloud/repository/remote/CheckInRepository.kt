package com.de.signcloud.repository.remote

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
        if (result.code == 200) {
            Result.Success(result)
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