package com.de.signcloud.ui.checkin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.de.signcloud.repository.remote.CheckInRepository

class CheckInListViewModel : ViewModel() {

    private val _checkInList = MutableLiveData<String>()

    val checkInList = Transformations.switchMap(_checkInList) {
        CheckInRepository.getCheckInList(it)
    }

    fun getCheckInList(code: String) {
        _checkInList.value = code
    }

}