package com.de.signcloud.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.Screen
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event

class CourseOperationViewModel : ViewModel() {

    val isStudent: Boolean
        get() = UserRepository.isUserStudent()

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

}


@Suppress("UNCHECKED_CAST")
class CourseOperationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseOperationViewModel::class.java)) {
            return CourseOperationViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}