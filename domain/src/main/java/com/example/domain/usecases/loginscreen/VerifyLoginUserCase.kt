package com.example.domain.usecases.loginscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository

class VerifyLoginUserCase(private val repository: UserRepository) {
    //    fun execute(userParams: LoginUserParams): Boolean {
//        val oldUser = repository.getLoginUser(userParams.loginParam)
//
//        return verifyUser(user = userParams, oldUser = oldUser)
//    }
    fun execute(userParams: LoginUserParams): Response {
        val resVal = try {
            val oldUser = repository.getLoginUser(userParams.loginParam)
            verifyUser(user = userParams, oldUser = oldUser)
            IOResponse.Success("Login Success", true)
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(ex.message)
        }
        return resVal
    }

    //    private fun verifyUser(user: LoginUserParams, oldUser: LoginUserModel?): Boolean {
    private fun verifyUser(user: LoginUserParams, oldUser: LoginUserModel?) {
        if (oldUser == null) {
//            return false
            throw Exception("This user is not registered")
        }
        if (user.loginParam != oldUser.login) {
//            false
            throw Exception("User login wrong")

        }
        if (user.passwordParam != oldUser.password) {
//            false
            throw Exception(message = "User password wrong")

        } else {
            user.loginParam == oldUser.login &&
                    user.passwordParam == oldUser.password
        }
    }
}