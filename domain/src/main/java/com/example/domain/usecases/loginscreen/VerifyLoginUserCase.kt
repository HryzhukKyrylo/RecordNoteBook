package com.example.domain.usecases.loginscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.LoginSomethingWentWrongResult
import com.example.domain.usecases.LoginSuccessResult

class VerifyLoginUserCase(private val repository: UserRepository) {
    fun execute(userParams: LoginUserParams): Response {
        val resVal = try {
            val oldUser = repository.getUserLogin(userParams.loginParam)
            verifyUser(user = userParams, oldUser = oldUser)
            IOResponse.Success(LoginSuccessResult, true)
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(LoginSomethingWentWrongResult(ex.message))
        }
        return resVal
    }
//TODO think about that - do better
    private fun verifyUser(user: LoginUserParams, oldUser: LoginUserModel?) {
        if (oldUser == null) {
            throw Exception("This user is not registered")
        }
        if (user.loginParam != oldUser.login) {
            throw Exception("User login wrong")

        }
        if (user.passwordParam != oldUser.password) {
            throw Exception("User password wrong")

        } else {
            user.loginParam == oldUser.login &&
                    user.passwordParam == oldUser.password
        }
    }
}