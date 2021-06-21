package com.de.signcloud.ui.course

import android.app.Application
import android.util.Log
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import java.util.concurrent.ExecutionException

class ScanCodeViewModel() : ViewModel() {

    val progressState: LiveData<Boolean> get() = _progressState
    private val _progressState = MutableLiveData(false)

    fun searchCode(code: String) {
        _progressState.value = true
        Log.d("ScanCodeViewModel", code)
        _progressState.value = false
    }

}

@Suppress("UNCHECKED_CAST")
class ScanCodeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanCodeViewModel::class.java)) {
            return ScanCodeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}