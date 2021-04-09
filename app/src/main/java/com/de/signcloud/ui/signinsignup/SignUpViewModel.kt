package com.de.signcloud.ui.signinsignup

import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.Screen.Home
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event

class SignUpViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    fun signUp(phone: String, password: String) {
        userRepository.signUp(phone, password)
        _navigateTo.value = Event(Home)
    }


    private val _validateCodePhone = MutableLiveData<String>()

    fun getValidateCode(phone: String) {
        _validateCodePhone.value = phone
    }

    val validateCodeLiveData = Transformations.switchMap(_validateCodePhone) {
        UserRepository.getValidate(it)
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