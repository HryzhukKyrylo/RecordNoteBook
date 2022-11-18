package com.example.recordnotebook.ui.detailscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.UserNotateModel
import com.example.recordnotebook.utils.SingleLiveEvent

class DetailScreenViewModel : ViewModel() {

    private val _selectUserModel: MutableLiveData<UserNotateModel> = SingleLiveEvent()
    val selectUserModel: LiveData<UserNotateModel> = _selectUserModel

    private val _isEnableRefactor: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEnableRefactor: LiveData<Boolean> = _isEnableRefactor

    fun transitionToCreate(loginParam: UserNotateModel?) {
        loginParam?.let {
            _selectUserModel.value = it
        }
    }

    fun reverEnableEdit() {
        _isEnableRefactor.value = !(_isEnableRefactor.value ?: false)
    }
}