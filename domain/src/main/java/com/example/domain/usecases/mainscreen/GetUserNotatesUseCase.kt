package com.example.domain.usecases.mainscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.MainSomethingWentWrongResult

class GetUserNotatesUseCase(
    private val repository: UserRepository,
) {
    fun execute(userParam: String): Response {
        val resVal = try {
            val list = repository.getUserNotates(userParam = userParam)
                .sortedByDescending { it.timeLastChange }
            IOResponse.Success(
                message = null, data = list
            )
        } catch (ex: Exception) {
            IOResponse.Error(
                errorMessage = MainSomethingWentWrongResult
            )
        }
        return resVal
    }
}