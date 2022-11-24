package com.example.domain.usecases.accountscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.*

class SaveNewPasswordUseCase(
    private val repository: UserRepository,
) {
    fun execute(
        login: String,
        curPass: String,
        newPass: String,
        confPass: String
    ): Response {
        //todo implement
        val res = try {
            val login = repository.getUserLogin(login)
            val password = login?.password ?: return IOResponse.Error(
                AccountCantFindOldPasswordResult
            )
            val loginName = login.login
            return verifyEmptyPasswords(curPass, newPass, confPass)
                ?: verifyPassword(password, curPass)
                ?: checkTheSamePassword(curPass, newPass)
                ?: confirmPassword(newPass, confPass)
                ?: savePassword(password, curPass, newPass, confPass, loginName)
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(AccountSomethingWentWrongExceptionResult(ex.message))
        }
        return res
    }

    private fun savePassword(
        password: String,
        curPass: String,
        newPass: String,
        confPass: String,
        loginName: String
    ): Response {
        return if (password == curPass && newPass == confPass) {
            val param = LoginUserParams(loginName, newPass)
            repository.saveNewPassword(param)
            IOResponse.Success(AccountSavedSuccessResult, null)
        } else {
            IOResponse.Error(AccountSavedErrorResult)
        }
    }

    private fun checkTheSamePassword(curPass: String, newPass: String): Response? {
        if (curPass == newPass) {
            return IOResponse.Error(AccountNewPasswordMatchesResult)
        }
        return null
    }

    private fun confirmPassword(newPass: String, confPass: String): Response? {
        if (newPass != confPass) {
            return IOResponse.Error(AccountConfirmPasswordWrongResult)
        }
        return null
    }

    private fun verifyPassword(password: String, curPass: String): Response? {
        if (password != curPass) {
            return IOResponse.Error(AccountCurrentPasswordWrongResult)
        }
        return null
    }

    private fun verifyEmptyPasswords(
        curPass: String,
        newPass: String,
        confPass: String
    ): Response? {
        if (curPass.trim().isEmpty()) {
            return IOResponse.Error(AccountCurrentPasswordEmptyResult)
        }
        if (newPass.trim().isEmpty()) {
            return IOResponse.Error(AccountNewEmptyPasswordResult)
        }
        if (confPass.trim().isEmpty()) {
            return IOResponse.Error(AccountConfirmEmptyPasswordResult)
        }
        return null
    }
}