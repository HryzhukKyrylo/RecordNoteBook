package com.example.recordnotebook.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel
import com.example.domain.usecases.mainscreen.GetUserNotatesUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val getUserNotatesUseCase: GetUserNotatesUseCase
) : ViewModel() {

    private val _listUserNotates: MutableLiveData<List<UserNotateModel>> = MutableLiveData()
    val listUserNotates: LiveData<List<UserNotateModel>> = _listUserNotates

    private val _itemClicked: MutableLiveData<UserNotateModel> = SingleLiveEvent()
    val itemClicked: LiveData<UserNotateModel> = _itemClicked

    fun loadData(userParam: String) {
        viewModelScope.launch {
            _listUserNotates.value = getUserNotatesUseCase.execute(userParam)
        }
    }

    fun transitionToDetail(data: UserNotateModel) {
        _itemClicked.value = data
    }
}