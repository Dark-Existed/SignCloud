package com.de.signcloud

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.de.signcloud.repository.local.UserDao
import com.de.signcloud.repository.remote.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun isUserSignIn(): Boolean {
        return UserDao.isUserSignIn()
    }

    fun readUserInfo() {
        UserRepository.readUser()
    }

}


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}