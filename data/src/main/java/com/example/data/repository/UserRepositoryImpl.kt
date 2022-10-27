package com.example.data.repository

import com.example.data.storage.models.LoginUserDTO
import com.example.data.storage.models.mapToModel
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository

class UserRepositoryImpl(private val prefStorage: SharedPreferencesStorage) : UserRepository {

    override fun getLoginUser(): LoginUserModel {
        val user = prefStorage.getLoginUser()
        return user.mapToModel()
    }

    override fun saveLoginUser(loginUserParams: LoginUserParams): Boolean {
        val userDTO = loginUserParams.mapToDTO()
        return prefStorage.saveLoginUser(userDTO)
    }

    private fun LoginUserParams.mapToDTO(): LoginUserDTO {
        return LoginUserDTO(
            login = this.loginParam,
            password = this.passwordParam
        )
    }
}