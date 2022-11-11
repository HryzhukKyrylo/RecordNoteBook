package com.example.domain.usecases.signupscreen

import com.example.domain.Response
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository

class SaveLoginUserUseCase(private val repository: UserRepository) {
    fun execute(loginUserParams: LoginUserParams): Response {
        return repository.saveLoginUser(loginUserParams = loginUserParams)
    }
}