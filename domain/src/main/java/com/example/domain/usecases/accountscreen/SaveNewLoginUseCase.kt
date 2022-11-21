package com.example.domain.usecases.accountscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserModel
import com.example.domain.models.toParam
import com.example.domain.repository.UserRepository

class SaveNewLoginUseCase(private val repository: UserRepository) {
    fun execute(oldName: String?, newName: String?): Response {
        try {
            validOldName(oldName)
            validNewName(newName)
            val isNoteTheSame = isNotTheSame(oldName, newName)
            if (isNoteTheSame) {
                val oldUser = oldName?.let { login -> repository.getLoginUser(login) }
                val resVal = if (oldUser != null) {
                    val res = changeLogNameLogin(oldName, newName, oldUser)
                    changeLogNameNotates(oldName, newName!!)
                    res
                } else {
                    IOResponse.Error("Can't find old login")
                }
                return resVal
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return IOResponse.Error(ex.message)
        } catch (messageEx: IllegalStateException) {
            messageEx.printStackTrace()
            return IOResponse.Error(messageEx.message)
        }
        return IOResponse.Error("Something went wrong")
    }

    private fun changeLogNameLogin(
        oldName: String,
        newName: String?,
        oldUser: LoginUserModel
    ): Response {
        val newLogin = oldUser.copy(login = newName!!)
        val newParam = newLogin.toParam()
        val res = repository.saveLoginUser(newParam)
        repository.deleteUserLogin(oldName)
        return res
    }

    private fun changeLogNameNotates(oldName: String, newName: String) {
        val oldList = repository.getUserNotates(oldName)
        val newList = oldList.map { item -> item.copy(userLogName = newName) }
        repository.saveAllUserNotates(newList)
        repository.removeUserAllNotates(oldName)
    }

    private fun validOldName(name: String?) {
        if (name == null) throw IllegalStateException("Old name is null")
        if (name.isEmpty()) throw IllegalStateException("Old name is empty")
        if (name.trim().isEmpty()) throw IllegalStateException("Old name is empty")
    }

    private fun validNewName(name: String?) {
        if (name == null) throw IllegalStateException("New name is null")
        if (name.isEmpty()) throw IllegalStateException("New name is empty")
        if (name.trim().isEmpty()) throw IllegalStateException("New name is empty")
    }

    private fun isNotTheSame(oldName: String?, newName: String?): Boolean {
        if (oldName != null && newName != null) {
            return oldName.trim() != newName.trim()
        }
        return false
    }
}