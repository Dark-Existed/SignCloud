package com.de.signcloud.ui.signinsignup

import android.util.Log
import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.Screen.Home
import com.de.signcloud.bean.ValidateCode
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {


    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    /**
     * Consider all sign ups successful
     */
    fun signUp(phone: String, password: String) {
        userRepository.signUp(phone, password)
        _navigateTo.value = Event(Home)
    }

    fun getValidateCode(phone: String) {
        val validateCodeResult = userRepository.getValidate(phone)
//        Log.d("SignUpViewModel", validateCodeResult.value.toString())
    }

}

class SignUpViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}