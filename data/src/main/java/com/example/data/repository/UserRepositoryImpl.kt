package com.example.data.repository

import com.example.data.storage.localstorage.LocalStorage
import com.example.data.storage.models.copyDTOWithNewData
import com.example.data.storage.models.createDTO
import com.example.data.storage.models.mapToDTO
import com.example.data.storage.models.mapToDomain
import com.example.domain.models.CreateUserParams
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel
import com.example.domain.repository.UserRepository

class UserRepositoryImpl(
    private val localStorage: LocalStorage
) : UserRepository {

    override fun getLoginUser(loginParam: String): LoginUserModel? {
        val user =
            localStorage.getLoginUser(userLogNameParam = loginParam)
        return user?.mapToDomain()
    }

    override fun saveLoginUser(loginUserParams: LoginUserParams): Boolean {
        val userDTO = loginUserParams.mapToDTO()
        return localStorage.saveLoginUser(userDTO)
    }

    override fun getUserNotates(userParam: String): List<UserNotateModel> {
        val listDTO = localStorage.getUserNotates(userLogNameParam = userParam)
        return listDTO.map { it.mapToDomain() }
    }

    override fun saveUserNotate(userParams: CreateUserParams): Boolean {
        val userDTO = if (userParams.isCreated) {
            val oldNotate = localStorage.getNotate(userParams.logName!!, userParams.createTimestamp)
            val newNotate = oldNotate.copyDTOWithNewData(
                logData = userParams.logData,
                privateInfo = userParams.privateInfo,
                timeLastChange = userParams.createTimestamp,
            )
            newNotate
        } else {
            userParams.createDTO()
        }
        return localStorage.saveUserNotate(userDTO)
    }

    override fun removeNotate(userNotateModel: UserNotateModel): Boolean {
        val userDTO = userNotateModel.mapToDTO()
        return localStorage.removeNotate(userDTO)
    }
}