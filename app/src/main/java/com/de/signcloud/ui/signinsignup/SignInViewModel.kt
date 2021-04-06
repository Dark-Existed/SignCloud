package com.de.signcloud.ui.signinsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.Screen
import com.de.signcloud.Screen.Home
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    fun signIn(phone: String, password: String) {
        userRepository.signIn(phone, password)
        _navigateTo.value = Event(Home)
    }

}

@Suppress("UNCHECKED_CAST")
class SignInViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}