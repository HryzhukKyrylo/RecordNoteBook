package com.example.recordnotebook.ui.accountscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.sessionapp.SessionAppMutable
import com.example.domain.IOResponse
import com.example.domain.models.LoginUserModel
import com.example.domain.usecases.accountscreen.GetUserLoginUseCase
import com.example.domain.usecases.accountscreen.SaveNewLoginUseCase
import com.example.domain.usecases.accountscreen.SaveNewPasswordUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountScreenViewModel(
    private val getUserLoginUseCase: GetUserLoginUseCase,
    private val saveNewLoginUseCase: SaveNewLoginUseCase,
    private val saveNewPasswordUseCase: SaveNewPasswordUseCase,
    private val sessionApp: SessionAppMutable,
) : ViewModel() {

    private val _userLoginData: MutableLiveData<LoginUserModel> =
        MutableLiveData(LoginUserModel.emptyLoginUserModel())
    val userLoginData: LiveData<LoginUserModel> = _userLoginData

    private val _showMessage: MutableLiveData<String> = SingleLiveEvent()
    val showMessage: LiveData<String> = _showMessage

    private val _isLogNameChanged: MutableLiveData<Boolean> = SingleLiveEvent()
    val isLogNameChanged: LiveData<Boolean> = _isLogNameChanged

    private val _isPasswordChanged: MutableLiveData<Boolean> = SingleLiveEvent()
    val isPasswordChanged: LiveData<Boolean> = _isPasswordChanged

    lateinit var sessionName: LiveData<String?>

    init {
        setSessionData()
    }

    private fun setSessionData() {
        sessionName = sessionApp.sessionName
    }

    fun loadData(data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resVal = getUserLoginUseCase.execute(data)
            when (resVal) {
                is IOResponse.Success -> {
                    val someData = resVal.data
                    someData?.let { loginUserData ->
                        if (loginUserData is LoginUserModel) {
                            _userLoginData.postValue(loginUserData)
                        }
                    }
                    resVal.message?.let { message ->
                        _showMessage.postValue(message)
                    }
                }
                is IOResponse.Error -> {
                    resVal.errorMessage?.let { message ->
                        _showMessage.postValue(message)
                    }
                }
            }
        }
    }

    fun changeUserLogName(data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //todo implement

            val resVal = saveNewLoginUseCase.execute(
                oldName = userLoginData.value?.login,
                newName = data
            )
            when (resVal) {
                is IOResponse.Success -> {
                    resVal.message?.let { message ->
                        _showMessage.postValue(message)
                    }
                    sessionApp.changeSessionName(data) //todo think how do that better
                    _isLogNameChanged.postValue(true)
                }
                is IOResponse.Error -> {
                    resVal.errorMessage?.let { message ->
                        _showMessage.postValue(message)
                    }
                }
            }

        }
    }

    fun saveNewPassword(curPass: String, newPass: String, confPass: String) {
        viewModelScope.launch(Dispatchers.IO) { //todo create own impl IO,Main ...
            userLoginData.value?.login?.let { login ->
                val resVal = saveNewPasswordUseCase.execute(
                    login,
                    curPass,
                    newPass,
                    confPass
                )
                when (resVal) {
                    is IOResponse.Success -> {
                        resVal.message?.let { data ->
                            _showMessage.postValue(data)
                        }
                        _isPasswordChanged.postValue(true)
                    }
                    is IOResponse.Error -> {
                        resVal.errorMessage?.let { data ->
                            _showMessage.postValue(data)
                        }
                    }
                }
            }

        }
    }
}