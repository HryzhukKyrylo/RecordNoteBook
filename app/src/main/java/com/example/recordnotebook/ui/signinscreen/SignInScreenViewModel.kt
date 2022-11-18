package com.example.recordnotebook.ui.signinscreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.IOResponse
import com.example.domain.models.LoginUserParams
import com.example.domain.usecases.loginscreen.VerifyLoginUserCase
import com.example.recordnotebook.R
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInScreenViewModel(
    private val context: Application,
    private val verifyLoginUserCase: VerifyLoginUserCase,
) : ViewModel() {
    private val _isVerifySuccess: MutableLiveData<Boolean> = SingleLiveEvent()
    val isVerifySuccess: LiveData<Boolean> = _isVerifySuccess

    private val _goToSignUpScreen: MutableLiveData<Boolean> = SingleLiveEvent()
    val goToSignUpScreen: LiveData<Boolean> = _goToSignUpScreen

    private val _isClearFields: MutableLiveData<Boolean> = SingleLiveEvent()
    val isClearFields: LiveData<Boolean> = _isClearFields

    private val _showMessage: MutableLiveData<String> = SingleLiveEvent()
    val showMessage: LiveData<String> = _showMessage

    private val _userValidData: MutableLiveData<LoginUserParams> = SingleLiveEvent()

    fun getUserValidData(): LoginUserParams? {
        return _userValidData.value
    }

    fun clearFields() {
        _isClearFields.value = true
    }

    fun verifyUserLogin(userParams: LoginUserParams) {
        val isValidLogin = checkLoginFields(userParams)
        if (isValidLogin) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = verifyLoginUserCase.execute(userParams = userParams)
                when (result) {
                    is IOResponse.Success -> {
                        //todo
                        _userValidData.postValue(userParams)
                        result.data?.let {
                            _isVerifySuccess.postValue(true)
                        }
                    }
                    is IOResponse.Error -> {
                        result.errorMessage?.let { _showMessage.postValue(it) }
                    }
                }

            }
        } else {
//            _isVerifySuccess.value = false
            _showMessage.value = context.getString(R.string.signing_screen_login_field_are_empty)
        }
    }

    private fun checkLoginFields(userParams: LoginUserParams): Boolean {
        return userParams.loginParam.trim().isNotEmpty()
                && userParams.passwordParam.trim().isNotEmpty()
    }

    fun navigateToSignUpScreen() {
        _goToSignUpScreen.value = true
    }

}