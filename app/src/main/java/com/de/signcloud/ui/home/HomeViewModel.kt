package com.de.signcloud.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.Screen.CreateCourse
import com.de.signcloud.bean.GetCoursesCreateResponse
import com.de.signcloud.repository.remote.CourseRepository
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event
import com.de.signcloud.utils.Result
import com.de.signcloud.utils.getOrNull

class HomeViewModel() : ViewModel() {


    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo


    fun navigateToCreateCourse() {
        _navigateTo.value = Event(CreateCourse)
    }

    val isStudent: Boolean
        get() = UserRepository.isUserStudent()


    private var _courseCreateList = CourseRepository.getCourseCreate()
    val courseCreateList = Transformations.map(_courseCreateList) {
        it?.getOrNull()?.courses ?: emptyList()
    }

    fun updateCourseList() {
        _courseCreateList = CourseRepository.getCourseCreate()
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}