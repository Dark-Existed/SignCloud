package com.de.signcloud.ui.checkin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.de.signcloud.repository.remote.CheckInRepository

class CourseStudentViewModel : ViewModel() {

    private val _courseStudent = MutableLiveData<String>()
    val courseMembers = Transformations.switchMap(_courseStudent) {
        CheckInRepository.getCourseStudents(it)
    }
    fun getCourseStudent(code: String) {
        _courseStudent.value = code
    }

}