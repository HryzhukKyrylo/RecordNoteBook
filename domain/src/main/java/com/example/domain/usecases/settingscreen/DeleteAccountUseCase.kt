package com.example.domain.usecases.settingscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.SettingScreenDeleteErrorResult
import com.example.domain.usecases.SettingScreenDeleteSuccessResult
import com.example.domain.usecases.SettingScreenSomethingWentWrongResult

class DeleteAccountUseCase(private val repository: UserRepository) {
    fun execute(logName: String): Response {
        val resVal = try {
            deleteAccount(logName)
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(errorMessage = SettingScreenSomethingWentWrongResult)
        }
        return resVal
    }

    private fun deleteAccount(logName: String): Response {
        val isDeleted = repository.removeUserLogin(userLogName = logName)
        val resVal = if (isDeleted) {
            repository.removeUserAllNotates(userLogName = logName)
            IOResponse.Success(
                message = SettingScreenDeleteSuccessResult,
                data = true
            )
        } else {
            IOResponse.Error(
                errorMessage = SettingScreenDeleteErrorResult
            )
        }
        return resVal
    }
}