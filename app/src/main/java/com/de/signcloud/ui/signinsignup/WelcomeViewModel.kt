package com.de.signcloud.ui.signinsignup

import android.util.Log
import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.Screen.*
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event
import com.de.signcloud.utils.getOrNull

class WelcomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> = _navigateTo

    private val _phoneNumber = MutableLiveData<String>()

    val isPhoneExistLivaData = Transformations.switchMap(_phoneNumber) {
        userRepository.isPhoneExist(it)
    }

    fun handleContinue(phone: String) {
        _phoneNumber.value = phone
    }

//    fun handleContinue(phone: String) {
//        if (userRepository.isKnownUserPhone(phone)) {
//            navigateToSignIn()
//        } else {
//            navigateToSignUp()
//        }
//    }

    fun navigateToSignUp() {
        _navigateTo.value = Event(SignUp)
    }

    fun navigateToSignIn() {
        _navigateTo.value = Event(SignIn)
    }

    fun navigateToSignInWithGithub() {
        _navigateTo.value = Event(SignInWithGithub)
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