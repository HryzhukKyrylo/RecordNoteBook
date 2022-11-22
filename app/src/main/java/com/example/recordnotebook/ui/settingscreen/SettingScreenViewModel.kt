package com.example.recordnotebook.ui.settingscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.sessionapp.SessionApp
import com.example.domain.IOResponse
import com.example.domain.usecases.settingscreen.DeleteAccountUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingScreenViewModel(
    private val deleteAccount: DeleteAccountUseCase,
    private val sessionApp: SessionApp,
) : ViewModel() {

    private val _goToRefactorAccount: MutableLiveData<Boolean> = SingleLiveEvent()
    val gotToRefactorAccount: LiveData<Boolean> = _goToRefactorAccount

    private val _goToLogIn: MutableLiveData<Boolean> = SingleLiveEvent()
    val goToLogIn: LiveData<Boolean> = _goToLogIn

    private val _showMessage: MutableLiveData<String> = SingleLiveEvent()
    val showMessage: LiveData<String> = _showMessage

    lateinit var sessionName: LiveData<String?>

    init {
        loadSessionData()
    }

    private fun loadSessionData() {
        sessionName = sessionApp.sessionName
    }

    fun deleteAccount(logName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resVal = deleteAccount.execute(logName = logName)
            when (resVal) {
                is IOResponse.Success -> {
                    resVal.data?.let {
                        val data = (it as Boolean)
                        _goToLogIn.postValue(data)
                    }
                    resVal.message?.let {
                        _showMessage.postValue(it)
                    }
                }
                is IOResponse.Error -> {
                    resVal.errorMessage?.let {
                        _showMessage.postValue(it)
                    }
                }
            }
        }
    }

    fun goToRefactorAccount() {
        _goToRefactorAccount.value = true
    }
}