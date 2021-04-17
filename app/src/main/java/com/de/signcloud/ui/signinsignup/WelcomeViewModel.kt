package com.de.signcloud.ui.signinsignup

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.de.signcloud.Screen
import com.de.signcloud.Screen.*
import com.de.signcloud.repository.local.UserDao
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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

    fun isUserSignIn() {
        viewModelScope.launch {
            val isUserSignIn  = async { UserDao.isUserSignIn() }
            if (isUserSignIn.await()) {
                navigateToHome()
            }
        }
    }


    fun navigateToHome() {
        _navigateTo.value = Event(Home)
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