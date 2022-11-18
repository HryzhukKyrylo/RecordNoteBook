package com.example.recordnotebook.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.domain.IOResponse
import com.example.domain.models.UserNotateModel
import com.example.domain.usecases.GetNightModeUseCase
import com.example.domain.usecases.SaveNightModeUseCase
import com.example.domain.usecases.mainscreen.GetUserNotatesUseCase
import com.example.domain.usecases.mainscreen.RemoveUserAllNotatesUseCase
import com.example.domain.usecases.mainscreen.RemoveUserNotateUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import com.example.recordnotebook.utils.setNightMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel(
    private val getUserNotatesUseCase: GetUserNotatesUseCase,
    private val removeUserNotateUseCase: RemoveUserNotateUseCase,
    private val removeUserAllNotatesUseCase: RemoveUserAllNotatesUseCase,
    private val getNightModeUseCase: GetNightModeUseCase,
    private val saveNightModeUseCase: SaveNightModeUseCase,
) : ViewModel() {

    private val _listUserNotates: MutableLiveData<List<UserNotateModel>> = MutableLiveData()
    val listUserNotates: LiveData<List<UserNotateModel>> = _listUserNotates

    private val _itemClicked: MutableLiveData<UserNotateModel> = SingleLiveEvent()
    val itemClicked: LiveData<UserNotateModel> = _itemClicked

    private val _isTransitionToCreate: MutableLiveData<String?> = SingleLiveEvent()
    val isTransitionToCreate: LiveData<String?> = _isTransitionToCreate

    private val _itemToRefactor: MutableLiveData<UserNotateModel> = SingleLiveEvent()
    val itemToRefactor: LiveData<UserNotateModel> = _itemToRefactor

    private val _showMessage: MutableLiveData<String> = SingleLiveEvent()
    val showMessage: LiveData<String> = _showMessage

    private val _isNightMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNightMode: LiveData<Boolean> = _isNightMode

    init {
        checkNightMode()
    }

    private fun checkNightMode() {
        viewModelScope.launch(Dispatchers.IO) {
            val nightMode = getNightModeUseCase.execute()
            val isNightModeOn = verifyNightModeOn(nightMode)
            _isNightMode.postValue(isNightModeOn)
        }
    }

    fun loadData(userLogName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _listUserNotates.postValue(getUserNotatesUseCase.execute(userLogName))
        }
    }

    fun transitionToDetail(data: UserNotateModel) {
        _itemClicked.value = data
    }

    fun transitionToCreate(isCreated: String?) {
        _isTransitionToCreate.value = isCreated
    }

    fun transitionToRefactor(itemModel: UserNotateModel) {
        _itemToRefactor.value = itemModel
    }

    fun removeNotate(userNotateModel: UserNotateModel) {
        viewModelScope.launch(Dispatchers.IO) {
            removeUserNotateUseCase.execute(userNotateModel)
        }
    }

    fun deleteAllUserNotate(userLogName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = removeUserAllNotatesUseCase.execute(userLogName)
            when (res) {
                is IOResponse.Success -> {
                    res.message?.let {
                        _showMessage.postValue(it)
                    }
                    _listUserNotates.postValue(emptyList())
                }
                is IOResponse.Error -> {
                    res.errorMessage?.let {
                        _showMessage.postValue(it)
                    }
                }
            }
        }
    }

    fun toggleNightMode() {
        viewModelScope.launch(Dispatchers.IO) {
            val isNightModeOn = _isNightMode.value ?: false
            _isNightMode.postValue(!isNightModeOn)
            val nightMode = if (!isNightModeOn) SharedPreferencesStorage.NIGHT_MODE_ON
            else SharedPreferencesStorage.NIGHT_MODE_OFF
            saveNightModeUseCase.execute(nightMode)
            withContext(Dispatchers.Main) {
                setNightMode(nightMode)
            }
        }
    }

    private fun verifyNightModeOn(resVal: Int): Boolean {
        val resVal = when (resVal) {
            SharedPreferencesStorage.NIGHT_MODE_ON -> {
                true
            }
            SharedPreferencesStorage.NIGHT_MODE_OFF -> {
                false
            }
            else -> {
                false
            }
        }
        return resVal
    }
}