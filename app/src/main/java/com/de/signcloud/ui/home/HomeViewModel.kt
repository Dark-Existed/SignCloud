package com.de.signcloud.ui.home

import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.Screen.CreateCourse
import com.de.signcloud.Screen.SearchCourse
import com.de.signcloud.repository.remote.CourseRepository
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event

class HomeViewModel() : ViewModel() {


    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo


    fun navigateToCreateCourse() {
        _navigateTo.value = Event(CreateCourse)
    }

    fun navigateToJoinCourse() {
        _navigateTo.value = Event(SearchCourse)
    }

    val isStudent: Boolean
        get() = UserRepository.isUserStudent()


    private val _courseCreateList = MutableLiveData<String>()

    val courseCreateList = Transformations.switchMap(_courseCreateList) {
        CourseRepository.getCourseCreate()
    }

    fun updateCourseList() {
        _courseCreateList.value = "update"
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