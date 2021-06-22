package com.de.signcloud.ui.checkin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CheckInViewModel : ViewModel() {



}

@Suppress("UNCHECKED_CAST")
class CheckInViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckInViewModel::class.java)) {
            return CheckInViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}