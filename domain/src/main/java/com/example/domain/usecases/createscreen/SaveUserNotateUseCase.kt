package com.example.domain.usecases.createscreen

import com.example.domain.models.CreateUserParams
import com.example.domain.repository.UserRepository

class SaveUserNotateUseCase(private val repository: UserRepository) {
    fun execute(userParams: CreateUserParams): Boolean {
        return repository.saveUserNotate(userParams)
    }
}