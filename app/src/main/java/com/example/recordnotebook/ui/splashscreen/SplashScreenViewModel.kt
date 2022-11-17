package com.example.recordnotebook.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.domain.usecases.GetNightModeUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import com.example.recordnotebook.utils.setNightMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenViewModel(
    private val getNightModeUseCase: GetNightModeUseCase,
) : ViewModel() {

    private val _goToNextScreen: MutableLiveData<Boolean> = SingleLiveEvent()
    val goToNextScreen: LiveData<Boolean> = _goToNextScreen

    private val _nightMode: MutableLiveData<Int> = SingleLiveEvent()
    val nightMode: LiveData<Int> = _nightMode

    init {
        checkNightMode()
        goToNextScreen()
    }

    private fun checkNightMode() {
        viewModelScope.launch(Dispatchers.IO) {
//            _nightMode.postValue(getNightModeUseCase.execute())
            val nightMode = getNightModeUseCase.execute()

            withContext(Dispatchers.Main) {
                setNightMode(nightMode)
            }
        }
    }

    fun goToNextScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(TIME_OUT_SPLASH_SCREEN)
            _goToNextScreen.postValue(true)
        }
    }

    companion object {
        const val TIME_OUT_SPLASH_SCREEN = 2000L
    }
}