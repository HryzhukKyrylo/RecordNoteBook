package com.example.domain.usecases.mainscreen

import com.example.domain.Response
import com.example.domain.models.UserNotateModel
import com.example.domain.repository.UserRepository

class RemoveUserAllNotatesUseCase(private val repository: UserRepository) {
    fun execute(userNotateModel: String): Response {
        return repository.removeUserAllNotates(userNotateModel)
    }
}