package com.example.domain.usecases.mainscreen

import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel
import com.example.domain.repository.UserRepository

class GetUserNotatesUseCase(private val repository: UserRepository) {
    fun execute(userParam: LoginUserParams): List<UserNotateModel> {
        return repository.getUserNotates(userParam = userParam)
    }
}