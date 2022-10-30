package com.example.recordnotebook.ui.signinscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.LoginUserParams
import com.example.domain.usecases.loginscreen.VerifyLoginUserCase
import com.example.recordnotebook.utils.SingleLiveEvent

class SignInScreenViewModel(
    private val verifyLoginUserCase: VerifyLoginUserCase,
) : ViewModel() {
    private val _isVerifySuccess: MutableLiveData<Boolean> = SingleLiveEvent()
    val isVerifySuccess: LiveData<Boolean> = _isVerifySuccess

    private val _goToSignUpScreen: MutableLiveData<Boolean> = SingleLiveEvent()
    val goToSignUpScreen: LiveData<Boolean> = _goToSignUpScreen

    private val _isClearFields: MutableLiveData<Boolean> = SingleLiveEvent()
    val isClearFields: LiveData<Boolean> = _isClearFields

    private val _userValidData: MutableLiveData<LoginUserParams> = SingleLiveEvent()

    fun getUserValidData(): LoginUserParams? {
        return _userValidData.value
    }

    fun clearFields() {
        _isClearFields.value = true
    }

    fun verifyUserLogin(userParams: LoginUserParams) {
        val result = verifyLoginUserCase.execute(userParams = userParams)
        _userValidData.value = userParams
        _isVerifySuccess.value = result
    }

    fun navigateToSignUpScreen() {
        _goToSignUpScreen.value = true
    }

}