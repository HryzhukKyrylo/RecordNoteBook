package com.example.recordnotebook.ui.detailscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.UserNotateModel
import com.example.recordnotebook.utils.SingleLiveEvent

class DetailScreenViewModel : ViewModel() {

    private val _selectUserModel: MutableLiveData<UserNotateModel> = SingleLiveEvent()
    val selectUserModel: LiveData<UserNotateModel> = _selectUserModel

    fun transitionToCreate(loginParam: UserNotateModel?) {
        loginParam?.let {
            _selectUserModel.value = it
        }
    }
}