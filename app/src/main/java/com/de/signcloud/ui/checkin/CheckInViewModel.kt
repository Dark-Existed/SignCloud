package com.de.signcloud.ui.checkin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption

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
}
