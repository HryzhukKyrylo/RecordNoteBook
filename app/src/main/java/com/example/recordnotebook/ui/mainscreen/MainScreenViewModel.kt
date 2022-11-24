package com.example.recordnotebook.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.sessionapp.SessionApp
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.domain.IOResponse
import com.example.domain.models.UserNotateModel
import com.example.domain.parser.ResultParser
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
    private val sessionApp: SessionApp,
    private val parseResult: ResultParser
) : ViewModel() {

    private val _listUserNotates: MutableLiveData<List<UserNotateModel>> = MutableLiveData()
    val listUserNotates: LiveData<List<UserNotateModel>> = _listUserNotates

    private val _clickedItemNotateModel: MutableLiveData<UserNotateModel> = SingleLiveEvent()
    val clickedItemNotateModel: LiveData<UserNotateModel> = _clickedItemNotateModel

    private val _isTransitionToCreate: MutableLiveData<Boolean> = SingleLiveEvent()
    val isTransitionToCreate: LiveData<Boolean> = _isTransitionToCreate

    private val _itemToRefactor: MutableLiveData<UserNotateModel> = SingleLiveEvent()
    val itemToRefactor: LiveData<UserNotateModel> = _itemToRefactor

    private val _showMessage: MutableLiveData<String> = SingleLiveEvent()
    val showMessage: LiveData<String> = _showMessage

    private val _isNightMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNightMode: LiveData<Boolean> = _isNightMode

    lateinit var sessionData: LiveData<String?>

    init {
        loadSessionData()
        checkNightMode()
    }

    private fun loadSessionData() {
        sessionData = sessionApp.sessionName
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
            val result = getUserNotatesUseCase.execute(userLogName)
            when (result) {
                is IOResponse.Success -> {
                    _listUserNotates.postValue(result.data as List<UserNotateModel>)
                }
                is IOResponse.Error -> {
                    parseResult(result.errorMessage)?.let {
                        _showMessage.postValue(it)
                    }
                }
            }
        }
    }

    fun transitionToDetail(data: UserNotateModel) {
        _clickedItemNotateModel.value = data
    }

    fun transitionToCreate() {
        _isTransitionToCreate.value = true
    }

    fun transitionToRefactor(itemModel: UserNotateModel) {
        _itemToRefactor.value = itemModel
    }

    fun removeNotate(userNotateModel: UserNotateModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = removeUserNotateUseCase.execute(userNotateModel)
            when (result) {
                is IOResponse.Success -> {
                    parseResult(result.message)?.let {
                        _showMessage.postValue(it)
                    }
                }
                is IOResponse.Error -> {
                    parseResult(result.errorMessage)?.let {
                        _showMessage.postValue(it)
                    }
                }
            }
        }
    }

    fun deleteAllUserNotate() {
        viewModelScope.launch(Dispatchers.IO) {
            val userLogName = sessionData.value
            userLogName?.let {
                val result = removeUserAllNotatesUseCase.execute(userLogName)
                when (result) {
                    is IOResponse.Success -> {
                        parseResult(result.message)?.let {
                            _showMessage.postValue(it)
                        }
                        _listUserNotates.postValue(emptyList())
                    }
                    is IOResponse.Error -> {
                        parseResult(result.errorMessage)?.let {
                            _showMessage.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun switchNightMode() {
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