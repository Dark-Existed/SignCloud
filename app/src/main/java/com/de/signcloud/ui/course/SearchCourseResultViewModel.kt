package com.de.signcloud.ui.course

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.repository.remote.CourseRepository

class SearchCourseResultViewModel : ViewModel() {

    private val _courseCode = MutableLiveData<String>()
    val joinCourseResult = Transformations.switchMap(_courseCode) {
        CourseRepository.joinCourse(it)
    }
    fun joinCourse(code: String) {
        _courseCode.value = code
    }

}


@Suppress("UNCHECKED_CAST")
class SearchCourseResultViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCourseResultViewModel::class.java)) {
            return SearchCourseResultViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}