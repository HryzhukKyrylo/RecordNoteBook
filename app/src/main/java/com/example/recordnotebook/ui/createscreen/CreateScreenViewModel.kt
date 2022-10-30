package com.example.recordnotebook.ui.createscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CreateUserParams
import com.example.domain.usecases.createscreen.SaveUserNotateUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateScreenViewModel(
    private val saveUserNotateUseCase: SaveUserNotateUseCase
) : ViewModel() {

    private val _isDataSaved: MutableLiveData<Boolean> = SingleLiveEvent()
    val isDataSaved: LiveData<Boolean> = _isDataSaved

    fun saveUserData(createUserParams: CreateUserParams) {
        viewModelScope.launch(Dispatchers.IO) {
            _isDataSaved.postValue(saveUserNotateUseCase.execute(createUserParams))
        }
    }
}