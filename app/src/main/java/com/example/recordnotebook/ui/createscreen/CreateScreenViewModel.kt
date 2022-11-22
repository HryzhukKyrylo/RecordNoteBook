package com.example.recordnotebook.ui.createscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.sessionapp.SessionApp
import com.example.domain.models.CreateUserParams
import com.example.domain.models.UserNotateModel
import com.example.domain.usecases.createscreen.SaveUserNotateUseCase
import com.example.recordnotebook.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateScreenViewModel(
    private val saveUserNotateUseCase: SaveUserNotateUseCase,
    private val sessionApp: SessionApp,
) : ViewModel() {

    private val _isDataSaved: MutableLiveData<Boolean> = SingleLiveEvent()
    val isDataSaved: LiveData<Boolean> = _isDataSaved

    lateinit var sessionName: LiveData<String?>

    init {
        setSessionData()
    }

    private fun setSessionData() {
        sessionName = sessionApp.sessionName
    }

    fun saveUserData(
        create: Boolean,
        logData: String,
        passData: String,
        userModel: UserNotateModel?
    ) {
        val isEmptyOrChangeData = checkEmptyOrChangeData(logData, passData, userModel)
        val userName = sessionName.value
        if (isEmptyOrChangeData) {
            val useParams = createUserParams(userName, create, logData, passData, userModel)
            viewModelScope.launch(Dispatchers.IO) {
                _isDataSaved.postValue(saveUserNotateUseCase.execute(useParams))
            }
        }
    }

    private fun checkEmptyOrChangeData(
        logData: String,
        passData: String,
        userModel: UserNotateModel?
    ): Boolean {
        return if (userModel == null) {
            logData.trim().isNotEmpty() || passData.trim().isNotEmpty()
        } else {
            userModel.logData != logData || userModel.privateInfo != passData
        }
    }

    private fun createUserParams(
        userName: String?,
        create: Boolean,
        logData: String,
        passData: String,
        userModel: UserNotateModel?
    ): CreateUserParams {
        val currentTime = System.currentTimeMillis()

        val resultUserParams = CreateUserParams(
            logName = userName,
            title = null,
            logData = logData,
            privateInfo = passData,
            createTimestamp = if (!create) currentTime else userModel?.timeCreate ?: currentTime,
            lastTimestamp = currentTime,
            isCreated = create
        )
        return resultUserParams
    }
}