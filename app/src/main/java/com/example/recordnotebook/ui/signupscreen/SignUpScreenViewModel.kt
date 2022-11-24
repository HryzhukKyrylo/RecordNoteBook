package com.example.recordnotebook.ui.signupscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.IOResponse
import com.example.domain.models.LoginUserParams
import com.example.domain.parser.ResultParser
import com.example.domain.usecases.signupscreen.SaveLoginUserUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpScreenViewModel(
    private val parseResult: ResultParser,
    private val saveLoginUserUseCase: SaveLoginUserUseCase
) : ViewModel() {

    private val _isClearFields: MutableLiveData<Boolean> = SingleLiveEvent()
    val isClearFields: LiveData<Boolean> = _isClearFields

    private val _isSavedSuccessful: MutableLiveData<Boolean> = SingleLiveEvent()
    val isSavedSuccessful: LiveData<Boolean> = _isSavedSuccessful

    private val _showMessage: MutableLiveData<String> = SingleLiveEvent()
    val showMessage: LiveData<String> = _showMessage

    fun clearFields() {
        _isClearFields.value = true
    }

    fun saveLoginUser(userParams: LoginUserParams) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = saveLoginUserUseCase.execute(userParams)
            when (result) {
                is IOResponse.Success -> {
                    _isSavedSuccessful.postValue(true)
                    parseResult(result.message)?.let {
                        _showMessage.postValue(it)
                    }
                }
                is IOResponse.Error -> {
                    parseResult(
                        result.errorMessage
                    )?.let { _showMessage.postValue(it) }
                }
            }
        }
    }
}