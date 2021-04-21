package com.de.signcloud.ui.createcourse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectSchoolViewModel : ViewModel() {
}


@Suppress("UNCHECKED_CAST")
class SelectSchoolViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectSchoolViewModel::class.java)) {
            return SelectSchoolViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
