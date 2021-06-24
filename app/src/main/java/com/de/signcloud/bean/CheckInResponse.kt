package com.de.signcloud.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

class CreateCheckInResponse(val code: Int, val message: String, val data: String)

class CurrentCheckInResponse(val code: Int, val message: String, val data: CheckInInfo?)

class GetCheckInList(val code: Int, val message: String, val data: Data) {
    inner class Data(@SerializedName("content") val checkInList: List<CheckInInfo>)
}

class CheckInInfo(
    val id: Int,
    val mode: String,
    val startTime: String,
    val endTime: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val isFinished: Int
) : Serializable