package com.de.signcloud.ui.signinsignup

import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.utils.Event

class BindPhoneViewModel() : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val bindPhoneEvent = MutableLiveData<BindPhoneEvent.OnBindPhone>()

//    val bindPhoneLiveData = Transformations.switchMap(bindPhoneEvent) {
//
//    }

    fun bindPhone(event: BindPhoneEvent.OnBindPhone) {
        bindPhoneEvent.value = event
    }

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
