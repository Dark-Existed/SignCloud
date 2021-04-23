package com.de.signcloud.ui.signinsignup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BindPhoneViewModel() : ViewModel() {

}


@Suppress("UNCHECKED_CAST")
class BindPhoneViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BindPhoneViewModel::class.java)) {
            return BindPhoneViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
