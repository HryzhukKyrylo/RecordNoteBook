package com.example.recordnotebook.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    private val _goToNextScreen: MutableLiveData<Boolean> = SingleLiveEvent()
    val goToNextScreen: LiveData<Boolean> = _goToNextScreen

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(TIME_OUT_SPLASH_SCREEN)
            _goToNextScreen.postValue(true)
        }
    }

    companion object {
        const val TIME_OUT_SPLASH_SCREEN = 2000L
    }
}