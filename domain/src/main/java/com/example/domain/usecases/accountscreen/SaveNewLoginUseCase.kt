package com.example.domain.usecases.accountscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserModel
import com.example.domain.models.toParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.AccountNameTheSameResult
import com.example.domain.usecases.AccountOldLoginResult
import com.example.domain.usecases.AccountSomethingWentWrongExceptionResult
import com.example.domain.usecases.AccountSomethingWentWrongResult

class SaveNewLoginUseCase(private val repository: UserRepository) {

    fun execute(oldName: String?, newName: String?): Response {
        try {
            validOldName(oldName)
            validNewName(newName)
            val isNoteTheSame = isNotTheSame(oldName, newName)
            if (isNoteTheSame) {
                val oldUser = oldName?.let { login -> repository.getUserLogin(login) }
                val resVal = if (oldUser != null) {
                    val res = changeLogNameLogin(oldName, newName, oldUser)
                    changeLogNameNotates(oldName, newName!!)
                    res
                } else {
                    IOResponse.Error(AccountOldLoginResult)
                }
                return resVal
            }else{
                return IOResponse.Error(AccountNameTheSameResult)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return IOResponse.Error(AccountSomethingWentWrongExceptionResult(ex.message))

        }
        return IOResponse.Error(AccountSomethingWentWrongResult)
    }

    private fun changeLogNameLogin(
        oldName: String,
        newName: String?,
        oldUser: LoginUserModel
    ): Response {
        val newLogin = oldUser.copy(login = newName!!)
        val newParam = newLogin.toParam()
        val res = try {
            repository.saveLoginUser(newParam)
            repository.removeUserLogin(oldName)
            IOResponse.Success(
                null, null
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(AccountSomethingWentWrongExceptionResult(ex.message))
        }
        return res
    }

    private fun changeLogNameNotates(oldName: String, newName: String) {
        val oldList = repository.getUserNotates(oldName)
        val newList = oldList.map { item -> item.copy(userLogName = newName) }
        repository.saveAllUserNotates(newList)
        repository.removeUserAllNotates(oldName)
    }
//TODO think about that - do better
    private fun validOldName(name: String?) {
        if (name == null) throw IllegalStateException("Old name is null")
        if (name.isEmpty()) throw IllegalStateException("Old name is empty")
        if (name.trim().isEmpty()) throw IllegalStateException("Old name is empty")
    }
//TODO think about that - do better

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