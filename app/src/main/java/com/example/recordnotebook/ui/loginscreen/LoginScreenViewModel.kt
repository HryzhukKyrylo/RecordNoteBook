package com.example.recordnotebook.ui.loginscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recordnotebook.utils.SingleLiveEvent

class LoginScreenViewModel : ViewModel() {
    val isClearFields: MutableLiveData<Boolean> = SingleLiveEvent()

    fun clearFields() {
        isClearFields.value = true
    }
}