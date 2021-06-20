package com.de.signcloud.ui.course

import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.repository.remote.CourseRepository
import com.de.signcloud.utils.Event

class SearchCourseViewModel : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _courseCode = MutableLiveData<String>()
    val course = Transformations.switchMap(_courseCode) {
        CourseRepository.getCourseByCode(it)
    }

    fun getCourse(code: String) {
        _courseCode.value = code
    }

}


@Suppress("UNCHECKED_CAST")
class SearchCourseViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCourseViewModel::class.java)) {
            return SearchCourseViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}