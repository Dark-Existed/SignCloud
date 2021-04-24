package com.de.signcloud.ui.signinsignup

import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.api.ServiceCreator
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.utils.Event


class SignInWithGithubViewModel : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> = _navigateTo

    val url =
        "https://github.com/login/oauth/authorize?client_id=f733d49acafb50cac307&state=STATE&redirect_uri=http://localhost:8080/callback"

    private val githubCode = MutableLiveData<String>()

    val signInWithGithubLiveData = Transformations.switchMap(githubCode) {
        UserRepository.signInWithGithubCode(it)
    }

    fun signInWithGithub(code: String) {
        githubCode.value = code
    }

    fun isRedirectUrl(url: String) = url.startsWith("http://localhost:8080/callback")

    fun getGithubCodeFromUrl(url: String): String? {
        val regex = Regex("[A-Za-z0-9]{20}")
        val matchResult = regex.find(url)
        return matchResult?.value
    }

    fun navigateToHome() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun navigateToBindPhone(githubId: Int) {

    }

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