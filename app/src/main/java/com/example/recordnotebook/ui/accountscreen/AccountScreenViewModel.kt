package com.example.recordnotebook.ui.accountscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.IOResponse
import com.example.domain.models.LoginUserModel
import com.example.domain.usecases.accountscreen.GetUserLoginUseCase
import com.example.domain.usecases.accountscreen.SaveNewLoginUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountScreenViewModel(
    private val getUserLoginUseCase: GetUserLoginUseCase,
    private val saveNewLoginUseCase: SaveNewLoginUseCase,
) : ViewModel() {

    private val _userLoginData: MutableLiveData<LoginUserModel> =
        MutableLiveData(LoginUserModel.emptyLoginUserModel())
    val userLoginData: LiveData<LoginUserModel> = _userLoginData

    private val _showMessage: MutableLiveData<String> = SingleLiveEvent()
    val showMessage: LiveData<String> = _showMessage

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
                }
                is IOResponse.Error -> {
                    resVal.errorMessage?.let { message ->
                        _showMessage.postValue(message)
                    }
                }
            }

        }
    }
}