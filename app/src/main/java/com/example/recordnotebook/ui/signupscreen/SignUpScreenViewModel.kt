package com.example.recordnotebook.ui.signupscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.LoginUserParams
import com.example.domain.usecases.signupscreen.SaveLoginUserUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpScreenViewModel(private val saveLoginUserUseCase: SaveLoginUserUseCase) : ViewModel() {

    private val _isClearFields: MutableLiveData<Boolean> = SingleLiveEvent()
    val isClearFields: LiveData<Boolean> = _isClearFields

    private val _isSavedSuccessful: MutableLiveData<Boolean> = SingleLiveEvent()
    val isSavedSuccessful: LiveData<Boolean> = _isSavedSuccessful

    fun clearFields() {
        _isClearFields.value = true
    }

    fun saveLoginUser(userParams: LoginUserParams) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = saveLoginUserUseCase.execute(userParams)
            _isSavedSuccessful.postValue(result)
        }
    }

    init {
        //TODO implement
    }
}