package com.example.domain.usecases

import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository

class SaveLoginUserUseCase(private val repository: UserRepository) {
    fun execute(loginUserParams: LoginUserParams): Boolean {
        return repository.saveLoginUser(loginUserParams = loginUserParams)
    }
}