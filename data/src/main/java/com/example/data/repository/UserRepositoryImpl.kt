package com.example.data.repository

import com.example.data.storage.localstorage.LocalStorage
import com.example.data.storage.models.LoginUserDTO
import com.example.data.storage.models.mapToDomain
import com.example.data.storage.models.mapToModel
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel
import com.example.domain.repository.UserRepository

class UserRepositoryImpl(
    private val prefStorage: SharedPreferencesStorage,
    private val localStorage: LocalStorage
) : UserRepository {

    override fun getLoginUser(): LoginUserModel {
        val user = prefStorage.getLoginUser()
        return user.mapToModel()
    }

    override fun saveLoginUser(loginUserParams: LoginUserParams): Boolean {
        val userDTO = loginUserParams.mapToDTO()
        return prefStorage.saveLoginUser(userDTO)
    }

    override fun getUserNotates(userParam: String): List<UserNotateModel> {
        val listDTO = localStorage.getUserNotates(userLogNameParam = userParam)
        return listDTO.map { it.mapToDomain() }
    }

    private fun LoginUserParams.mapToDTO(): LoginUserDTO {
        return LoginUserDTO(
            login = this.loginParam,
            password = this.passwordParam
        )
    }
}