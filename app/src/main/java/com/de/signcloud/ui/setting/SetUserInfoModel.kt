package com.de.signcloud.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.ui.components.textfieldstate.GenerateNotNullState

class SetUserInfoModel : ViewModel() {

    private val _textState = TextState()
    val textState: TextState
        get() = _textState


    private val _setUserInfo = MutableLiveData<String>()
    val setUserInfoResult = Transformations.switchMap(_setUserInfo) {
        UserRepository.setUserInfo(_textState.inoState.text, _textState.userNameState.text)
    }

    fun setUserInfo() {
        _setUserInfo.value = "update"
    }

}

class TextState {
    private val _inoState = GenerateNotNullState()
    val inoState
        get() = _inoState

    private val _userNameState = GenerateNotNullState()
    val userNameState
        get() = _userNameState

    init {
        _inoState.text = UserRepository.user.ino
        _userNameState.text = UserRepository.user.name
    }
}