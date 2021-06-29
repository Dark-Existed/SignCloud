package com.de.signcloud.ui.setting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.repository.local.UserDao
import com.de.signcloud.repository.remote.UserRepository

class ChangeRoleViewModel : ViewModel() {

    private val userType = listOf("student", "teacher")

    fun initSelectedIndex(): Int {
        val curRole = UserDao.getCurRole()
        return userType.indexOf(curRole)
    }

    private val _role = MutableLiveData<String>()
    val roleChange = Transformations.switchMap(_role) {
        UserRepository.setUserDefaultRole(_role.value ?: "")
    }
    fun changeRole(roleType: Int) {
        val role = userType[roleType]
        _role.value = role
    }

}


@Suppress("UNCHECKED_CAST")
class ChangeRoleViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeRoleViewModel::class.java)) {
            return ChangeRoleViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
