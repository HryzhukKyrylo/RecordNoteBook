package com.example.domain.usecases.loginscreen

import com.example.domain.models.LoginUserModel
import com.example.domain.repository.UserRepository

class GetLoginUserUseCase(private val repository: UserRepository) {
    fun execute(): LoginUserModel {
        return repository.getLoginUser()
    }
}