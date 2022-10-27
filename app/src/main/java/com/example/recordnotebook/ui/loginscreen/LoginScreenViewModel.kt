package com.example.recordnotebook.ui.loginscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.LoginUserParams
import com.example.domain.usecases.SaveLoginUserUseCase
import com.example.domain.usecases.loginscreen.GetLoginUserUseCase
import com.example.domain.usecases.loginscreen.VerifyLoginUserCase
import com.example.recordnotebook.utils.SingleLiveEvent

class LoginScreenViewModel(
    val saveLoginUserUseCase: SaveLoginUserUseCase,
    val getLoginUserUseCase: GetLoginUserUseCase,
    val verifyLoginUserCase: VerifyLoginUserCase
) : ViewModel() {
    private val _isVerifySuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isVerifySuccess: LiveData<Boolean> = _isVerifySuccess

    val isClearFields: MutableLiveData<Boolean> = SingleLiveEvent()

    fun clearFields() {
        isClearFields.value = true
    }

    fun verifyUserLogin(userParams: LoginUserParams) {
        val result = verifyLoginUserCase.execute(userParams = userParams)
        _isVerifySuccess.value = result
    }
}