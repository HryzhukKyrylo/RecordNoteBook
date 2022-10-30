package com.example.domain.usecases.mainscreen

import com.example.domain.models.UserNotateModel
import com.example.domain.repository.UserRepository

class RemoveUserNotateUseCase(private val repository: UserRepository) {
    fun execute(userNotateModel: UserNotateModel): Boolean {
        return repository.removeNotate(userNotateModel)
    }
}