package com.de.signcloud.ui.checkin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.de.signcloud.repository.remote.CheckInRepository
import com.de.signcloud.repository.remote.UserRepository

class CheckInHistoryViewModel : ViewModel() {

    private val _courseCode = MutableLiveData<String>()
    val checkInHistory = Transformations.switchMap(_courseCode) {
        CheckInRepository.getStudentCheckInHistory(it, UserRepository.user.id)
    }
    fun getCheckInHistory(courseCode:String) {
        _courseCode.value = courseCode
    }

}