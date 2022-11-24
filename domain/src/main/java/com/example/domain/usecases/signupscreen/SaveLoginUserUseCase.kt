package com.example.domain.usecases.signupscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.*

class SaveLoginUserUseCase(private val repository: UserRepository) {
    fun execute(loginUserParams: LoginUserParams): Response {
        return checkIsFieldsEmpty(loginUserParams)
            ?: saveLogin(loginUserParams)
            ?: IOResponse.Error(errorMessage = SignUpLogIsExistResult)
    }

    private fun saveLogin(loginUserParams: LoginUserParams): Response? {
        try {
            val logs =
                repository.getUserLogin(loginParam = loginUserParams.loginParam)
            if (logs == null) {
                repository.saveLoginUser(loginUserParams)
                return IOResponse.Success(
                    message = SignUpSavedSuccessResult,
                    data = null
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(errorMessage = SignUpSavedErrorResult)
        }
        return null
    }

    private fun checkIsFieldsEmpty(loginUserParams: LoginUserParams): Response? {
        if (loginUserParams.loginParam.trim().isEmpty()) {
            return IOResponse.Error(SignUpLoginIsEmptyResult)
        }
        if (loginUserParams.passwordParam.trim().isEmpty()) {
            return IOResponse.Error(SignUpPasswordIsEmptyResult)
        }
        return null
    }
}