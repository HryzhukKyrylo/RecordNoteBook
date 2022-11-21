package com.example.domain.usecases.accountscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.repository.UserRepository

class GetUserLoginUseCase(private val repository: UserRepository) {
    fun execute(userParam: String): Response {

        val oldUser = repository.getLoginUser(userParam)
        val resVal = if (oldUser != null) {
            IOResponse.Success(null, oldUser)
        } else {
            IOResponse.Error("Can't find user")
        }
        return resVal
    }
}