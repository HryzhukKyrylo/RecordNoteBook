package com.example.domain.usecases.loginscreen

import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository

class VerifyLoginUserCase(private val repository: UserRepository) {
    fun execute(userParams: LoginUserParams): Boolean {
        val oldUser = repository.getLoginUser()
        return verifyUser(user = userParams, oldUser = oldUser)
    }

    private fun verifyUser(user: LoginUserParams, oldUser: LoginUserModel): Boolean {
        return if (user.loginParam != oldUser.login) {
            false
        } else user.loginParam == oldUser.login && user.passwordParam == oldUser.password
    }
}