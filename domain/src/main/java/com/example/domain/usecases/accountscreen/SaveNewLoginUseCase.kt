package com.example.domain.usecases.accountscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.toParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.*

class SaveNewLoginUseCase(private val repository: UserRepository) {

    fun execute(oldName: String?, newName: String?): Response {
        try {
            val resVal = validOldName(oldName)
                ?: validNewName(newName)
                ?: isNotTheSame(oldName!!, newName!!)
                ?: changeLogNameLogin(oldName = oldName!!, newName = newName!!)
                ?: changeLogNameNotates(oldName!!, newName!!)
            return if (resVal == null) {
                IOResponse.Success(null, null)
            } else {
                resVal
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return IOResponse.Error(AccountSomethingWentWrongExceptionResult(ex.message))
        }
    }

    private fun changeLogNameLogin(
        oldName: String,
        newName: String,
    ): Response? {
        val oldUser = repository.getUserLogin(oldName)
        val newLogin = oldUser?.copy(login = newName!!)
        val newParam = newLogin?.toParam()
        try {
            newParam?.let {
                repository.saveLoginUser(newParam)
                repository.removeUserLogin(oldName)
            }
            if (newParam == null) {
                return IOResponse.Error(AccountSomethingWentWrongResult)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return IOResponse.Error(AccountSomethingWentWrongExceptionResult(ex.message))
        }
        return null
    }

    private fun changeLogNameNotates(oldName: String, newName: String): Response? {
        try {

            val oldList = repository.getUserNotates(oldName)
            val newList = oldList.map { item -> item.copy(userLogName = newName) }
            repository.saveAllUserNotates(newList)
            repository.removeUserAllNotates(oldName)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return IOResponse.Error(AccountSomethingWentWrongExceptionResult(ex.message))
        }
        return null
    }

    private fun validOldName(name: String?): Response? {
        if (name == null) {
            return IOResponse.Error(AccountOldNameIsNullResult)
        }
        if (name.isEmpty()) {
            return IOResponse.Error(AccountOldNameIsEmptyResult)
        }
        if (name.trim().isEmpty()) {
            return IOResponse.Error(AccountOldNameIsEmptyResult)
        }
        return null
    }

    private fun validNewName(name: String?): Response? {
        if (name == null) {
            return IOResponse.Error(AccountNewNameIsNullResult)
        }
        if (name.isEmpty()) {
            return IOResponse.Error(AccountNewNameIsEmptyResult)
        }
        if (name.trim().isEmpty()) {
            return IOResponse.Error(AccountNewNameIsEmptyResult)
        }
        return null
    }

    private fun isNotTheSame(oldName: String, newName: String): Response? {
        if (oldName.trim() == newName.trim()) {
            return IOResponse.Error(AccountNameTheSameResult)
        }
        return null
    }
}