package com.de.signcloud.ui.signinsignup

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.SignCloudApplication
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event

class ResetPasswordViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo


    private val resetPassword = MutableLiveData<ResetPasswordEvent.ResetPassword>()

    val resetPasswordLiveData = Transformations.switchMap(resetPassword) {
        userRepository.resetPassword(it.phone, it.password, it.validateCode)
    }

    fun resetPassword(event: ResetPasswordEvent.ResetPassword) {
        resetPassword.value = event
    }


    private val _validateCodePhone = MutableLiveData<String>()

    fun getValidateCode(phone: String) {
        _validateCodePhone.value = phone
    }

    val validateCodeLiveData = Transformations.switchMap(_validateCodePhone) {
        countDown()
        userRepository.getValidate(it)
    }

    private val _validateCountDown = MutableLiveData(60)

    val validateButtonText = Transformations.map(_validateCountDown) {
        if (it >= 60) {
            SignCloudApplication.context.getString(R.string.get_validate_code)
        } else {
            it.toString() + "s"
        }
    }

    val isValidateButtonClickable = Transformations.map(_validateCountDown) {
        it >= 60
    }

    private fun countDown() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _validateCountDown.value = _validateCountDown.value?.minus(1)
            }

            override fun onFinish() {
                _validateCountDown.value = 60
            }
        }.start()
    }
}


class ResetPasswordViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResetPasswordViewModel::class.java)) {
            return ResetPasswordViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}