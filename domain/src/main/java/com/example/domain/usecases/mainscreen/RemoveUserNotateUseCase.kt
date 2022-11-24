package com.example.domain.usecases.mainscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.UserNotateModel
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.MainDeleteErrorResult
import com.example.domain.usecases.MainDeleteSuccessResult
import com.example.domain.usecases.MainSomethingWentWrongResult

class RemoveUserNotateUseCase(private val repository: UserRepository) {
    fun execute(userNotateModel: UserNotateModel): Response {
        val res = try {
            val resVal = repository.removeNotate(userNotateModel)
            if (resVal) {
                IOResponse.Success(
                    MainDeleteSuccessResult,
                    null
                )
            } else {
                IOResponse.Error(MainDeleteErrorResult)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(MainSomethingWentWrongResult)
        }
        return res
    }
}