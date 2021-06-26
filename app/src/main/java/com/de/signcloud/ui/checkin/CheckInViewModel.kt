package com.de.signcloud.ui.checkin

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.de.signcloud.R
import com.de.signcloud.repository.remote.CheckInRepository
import com.instacart.library.truetime.TrueTime
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class CheckInViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _latitude = MutableLiveData<Double>()
    private val _longitude = MutableLiveData<Double>()
    private val _addressName = MutableLiveData<String>()
    val addressName
        get() = _addressName

    private val locationClient = LocationClient(context)
    private val listener = CustomLocationListener()

    init {
        locationClient.registerLocationListener(listener)
    }

    fun getLocation() {
        val option = LocationClientOption()
        option.isOpenGps = true
        option.setIsNeedAddress(true)
        option.isNeedNewVersionRgc = true
        locationClient.locOption = option
        locationClient.start()
    }

    inner class CustomLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation?) {
            _latitude.value = location?.latitude ?: -1.0
            _longitude.value = location?.longitude ?: -1.0
            _addressName.value = "${location?.city}${location?.district}${location?.street}"
            val errorCode = location?.locType
            Log.d("CreateCheckInViewModel", errorCode.toString())
        }
    }

    private val _countDown = MutableLiveData<Long>()
    val countDownInt
        get() = _countDown
    val countDownText = Transformations.map(_countDown) {
        if (it < 0) {
            application.getString(R.string.time_end)
        } else {
            it.toString() + "s"
        }
    }

    fun beginCountDown(endTime: String) {
        val simpleDataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val endDate = simpleDataFormat.parse(endTime)
        val trueDate = TrueTime.now()
        val time = endDate?.time?.minus(trueDate.time) ?: -1
        _countDown.value = (time / 1000)
        countDown(time)
    }

    private fun countDown(time: Long) {
        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _countDown.value = _countDown.value?.minus(1)
            }

            override fun onFinish() {
                _countDown.value = -1
            }
        }.start()
    }

    private val _destination = MutableLiveData<LatLng>()
    fun setDestination(location: LatLng) {
        _destination.value = location
    }

    private val _checkInId = MutableLiveData<Int>()
    val checkInResult = Transformations.switchMap(_checkInId) { checkInId ->
        val latitude = _latitude.value ?: -1.0
        val longitude = _longitude.value ?: -1.0
        val curLocation = LatLng(latitude, longitude)
        SDKInitializer.initialize(context)
        val distance = DistanceUtil.getDistance(curLocation, _destination.value)
        CheckInRepository.checkIn(checkInId, BigDecimal(latitude), BigDecimal(longitude), distance)
    }

    fun checkIn(checkInId: Int) {
        _checkInId.value = checkInId
    }

}
