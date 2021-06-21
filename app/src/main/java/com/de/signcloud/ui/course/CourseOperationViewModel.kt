package com.de.signcloud.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.repository.remote.UserRepository

class CourseOperationViewModel : ViewModel() {

    val isStudent: Boolean
        get() = UserRepository.isUserStudent()

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