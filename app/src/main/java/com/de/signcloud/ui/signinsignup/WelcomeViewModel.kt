package com.de.signcloud.ui.signinsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.Screen
import com.de.signcloud.Screen.SignIn
import com.de.signcloud.Screen.SignUp
import com.de.signcloud.utils.Event

class WelcomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> = _navigateTo

    fun handleContinue(phone: String) {
        if (userRepository.isKnownUserPhone(phone)) {
            _navigateTo.value = Event(SignIn)
        } else {
            _navigateTo.value = Event(SignUp)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class WelcomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}