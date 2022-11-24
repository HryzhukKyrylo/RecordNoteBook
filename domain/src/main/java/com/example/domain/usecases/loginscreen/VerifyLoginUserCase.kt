package com.example.domain.usecases.loginscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.*

class VerifyLoginUserCase(private val repository: UserRepository) {
    fun execute(userParams: LoginUserParams): Response {
        val resVal = try {
            return checkLoginFields(userParams)
                ?: verifyUser(user = userParams)
                ?: IOResponse.Error(LoginFailResult)
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(LoginSomethingWentWrongResult(ex.message))
        }
        return resVal
    }

    private fun checkLoginFields(userParams: LoginUserParams): Response? {
        if (userParams.loginParam.trim().isEmpty()) {
            return IOResponse.Error(LoginLoginIsEmptyResult)
        }
        if (userParams.passwordParam.trim().isEmpty()) {
            return IOResponse.Error(LoginPasswordIsEmptyResult)
        }
        return null
    }

    private fun verifyUser(user: LoginUserParams): Response? {
        val oldUser = repository.getUserLogin(user.loginParam)

        if (oldUser == null) {
            return IOResponse.Error(LoginIsNotRegisteredResult)
        }
        if (user.loginParam != oldUser.login) {
            return IOResponse.Error(LoginLoginWrongResult)
        }
        if (user.passwordParam != oldUser.password) {
            return IOResponse.Error(LoginPasswordWrongResult)

        } else {
            val isSuccess = user.loginParam == oldUser.login &&
                    user.passwordParam == oldUser.password
            if (isSuccess) return IOResponse.Success(LoginSuccessResult, true)
        }
        return null
    }
}