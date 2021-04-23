package com.de.signcloud.ui.signinsignup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.api.ServiceCreator


class SignInWithGithubViewModel : ViewModel() {

    val url = ServiceCreator.SIGN_IN_WITH_GITHUB_URL

}

@Suppress("UNCHECKED_CAST")
class SignInWithGithubViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInWithGithubViewModel::class.java)) {
            return SignInWithGithubViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}