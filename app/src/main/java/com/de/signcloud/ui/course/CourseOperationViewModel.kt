package com.de.signcloud.ui.course

import android.util.Log
import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.repository.remote.CheckInRepository
import com.de.signcloud.repository.remote.CourseRepository
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event

class CourseOperationViewModel : ViewModel() {

    val isStudent: Boolean
        get() = UserRepository.isUserStudent()

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _courseCourse = MutableLiveData<String>()
    val currentCheckIn = Transformations.switchMap(_courseCourse) {
        CheckInRepository.getCurrentCheckIn(it)
    }

    fun getCurrentCheckIn(courseCode: String) {
        _courseCourse.value = courseCode
    }

    private val _deleteCourse = MutableLiveData<String>()
    val deleteCourseResponse = Transformations.switchMap(_deleteCourse) {
        CourseRepository.deleteCourse(it)
    }
    fun deleteCourse(courseCode: String) {
        _deleteCourse.value = courseCode
    }

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