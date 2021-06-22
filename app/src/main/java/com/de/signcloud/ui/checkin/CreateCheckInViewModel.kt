package com.de.signcloud.ui.checkin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.ui.course.CreateCourseViewModel

class CreateCheckInViewModel : ViewModel() {

}

@Suppress("UNCHECKED_CAST")
class CreateCheckInViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCheckInViewModel::class.java)) {
            return CreateCheckInViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}