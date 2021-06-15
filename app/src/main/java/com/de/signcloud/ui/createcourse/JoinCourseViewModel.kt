package com.de.signcloud.ui.createcourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.Screen
import com.de.signcloud.utils.Event

class JoinCourseViewModel : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo


}



@Suppress("UNCHECKED_CAST")
class JoinCourseViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JoinCourseViewModel::class.java)) {
            return JoinCourseViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}