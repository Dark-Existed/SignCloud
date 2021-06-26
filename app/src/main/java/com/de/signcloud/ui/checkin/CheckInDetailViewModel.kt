package com.de.signcloud.ui.checkin

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.instacart.library.truetime.TrueTime
import java.text.SimpleDateFormat
import java.util.*

class CheckInDetailViewModel : ViewModel() {


    private val _countDown = MutableLiveData<Long>()

    val isEnable = Transformations.map(_countDown) {
        if (_mode.value == "time") {
            _countDown.value!! >= 0
        } else {
            true
        }
    }

    private val _mode = MutableLiveData<String>()

    fun setMode(mode: String) {
        _mode.value = mode
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
}