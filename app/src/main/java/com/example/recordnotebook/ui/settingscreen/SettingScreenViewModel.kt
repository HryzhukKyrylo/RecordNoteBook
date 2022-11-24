package com.example.recordnotebook.ui.settingscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.sessionapp.SessionApp
import com.example.domain.IOResponse
import com.example.domain.parser.ResultParser
import com.example.domain.usecases.settingscreen.DeleteAccountUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingScreenViewModel(
    private val deleteAccount: DeleteAccountUseCase,
    private val sessionApp: SessionApp,
    private val parseResult: ResultParser,
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

    fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            val logName = sessionName.value
            logName?.let { name ->
                val resVal = deleteAccount.execute(logName = name)
                when (resVal) {
                    is IOResponse.Success -> {
                        resVal.data?.let {
                            val data = (it as Boolean)
                            _goToLogIn.postValue(data)
                        }
                        parseResult(resVal.message)?.let {
                            _showMessage.postValue(it)
                        }
                    }
                    is IOResponse.Error -> {
                        parseResult(resVal.errorMessage)?.let {
                            _showMessage.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun goToRefactorAccount() {
        _goToRefactorAccount.value = true
    }
}