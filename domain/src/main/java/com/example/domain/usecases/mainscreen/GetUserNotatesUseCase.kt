package com.example.domain.usecases.mainscreen

import com.example.domain.models.UserNotateModel
import com.example.domain.repository.UserRepository

class GetUserNotatesUseCase(private val repository: UserRepository) {
    fun execute(userParam: String): List<UserNotateModel> {
        val list = repository.getUserNotates(userParam = userParam).sortedByDescending { it.timeLastChange }
        return list
    }
}