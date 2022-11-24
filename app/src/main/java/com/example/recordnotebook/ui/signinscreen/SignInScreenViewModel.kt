package com.example.recordnotebook.ui.signinscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.sessionapp.SessionAppMutable
import com.example.domain.IOResponse
import com.example.domain.models.LoginUserParams
import com.example.domain.parser.ResultParser
import com.example.domain.usecases.loginscreen.VerifyLoginUserCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInScreenViewModel(
    private val parseResult: ResultParser,
    private val verifyLoginUserCase: VerifyLoginUserCase,
    private val sessionApp: SessionAppMutable,
) : ViewModel() {
    private val _isVerifySuccess: MutableLiveData<Boolean> = SingleLiveEvent()
    val isVerifySuccess: LiveData<Boolean> = _isVerifySuccess

    private val _goToSignUpScreen: MutableLiveData<Boolean> = SingleLiveEvent()
    val goToSignUpScreen: LiveData<Boolean> = _goToSignUpScreen

    private val _isClearFields: MutableLiveData<Boolean> = SingleLiveEvent()
    val isClearFields: LiveData<Boolean> = _isClearFields

    private val _showMessage: MutableLiveData<String> = SingleLiveEvent()
    val showMessage: LiveData<String> = _showMessage

    fun clearFields() {
        _isClearFields.value = true
    }

    fun verifyUserLogin(userParams: LoginUserParams) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = verifyLoginUserCase.execute(userParams = userParams)
            when (result) {
                is IOResponse.Success -> {
                    result.data?.let {
                        _isVerifySuccess.postValue(true)
                    }
                    parseResult(result.message)?.let {
                        _showMessage.postValue(it)
                    }
                    sessionApp.setSessionName(userParams.loginParam)
                }
                is IOResponse.Error -> {
                    parseResult(result.errorMessage)?.let {
                        _showMessage.postValue(it)
                    }
                }
            }

        }
    }


    fun navigateToSignUpScreen() {
        _goToSignUpScreen.value = true
    }

}