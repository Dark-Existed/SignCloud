package com.de.signcloud.ui.checkin

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.baidu.location.*
import com.de.signcloud.repository.remote.CheckInRepository
import java.math.BigDecimal


class CreateCheckInViewModel(application: Application) : AndroidViewModel(application) {

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

    private val _courseCreate = MutableLiveData<CreateCheckInData>()

    fun createCheckIn(data: CreateCheckInData) {
        _courseCreate.value = data
    }

    val createCheckInLivaData = Transformations.switchMap(_courseCreate) {
        val latitude = BigDecimal(_latitude.value ?: -1.0)
        val longitude = BigDecimal(_longitude.value ?: -1.0)
        CheckInRepository.createCheckIn(it.code, it.mode, it.minutes, latitude, longitude)
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

}


class CreateCheckInData(val code: String, val mode: String, val minutes: Int = 0)
