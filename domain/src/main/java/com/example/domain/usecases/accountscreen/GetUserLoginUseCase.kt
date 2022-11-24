package com.example.domain.usecases.accountscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.AccountCantFindResult

class GetUserLoginUseCase(private val repository: UserRepository) {
    fun execute(userParam: String): Response {
        val oldUser = repository.getUserLogin(userParam)
        val resVal = if (oldUser != null) {
            IOResponse.Success(null, oldUser)
        } else {
            IOResponse.Error(AccountCantFindResult)
        }
        return resVal
    }
}