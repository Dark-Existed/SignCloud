package com.de.signcloud.ui.home

import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.Screen.*
import com.de.signcloud.repository.local.UserDao
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

    fun navigateToChangeRole() {
        _navigateTo.value = Event(ChangeRole)
    }

    fun navigateToScanCode() {
        _navigateTo.value = Event(ScanCode)
    }

    fun navigateToCourseOperation() {
        _navigateTo.value = Event(CourseOperation)
    }

    val isStudent: Boolean
        get() = UserRepository.isUserStudent()


    private val _courseCreateList = MutableLiveData<String>()

    val courseCreateList = Transformations.switchMap(_courseCreateList) {
        CourseRepository.getCourseCreate()
    }

    fun updateCreateCourseList() {
        _courseCreateList.value = "update"
    }


    private val _courseJoinedList = MutableLiveData<String>()
    val courseJoinedList = Transformations.switchMap(_courseJoinedList) {
        CourseRepository.getCourseJoined()
    }

    fun updateJoinedCourseList() {
        _courseJoinedList.value = "update"
    }


    fun signOut() {
        UserDao.signOut()
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