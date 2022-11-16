package com.example.data.repository

import com.example.data.storage.localstorage.LocalStorage
import com.example.data.storage.models.copyDTOWithNewData
import com.example.data.storage.models.createDTO
import com.example.data.storage.models.mapToDTO
import com.example.data.storage.models.mapToDomain
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.domain.Response
import com.example.domain.models.CreateUserParams
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel
import com.example.domain.repository.UserRepository

class UserRepositoryImpl(
    private val localStorage: LocalStorage,
    private val preferencesStorage: SharedPreferencesStorage,
) : UserRepository {

    override fun getLoginUser(loginParam: String): LoginUserModel? {
        val user =
            localStorage.getLoginUser(userLogNameParam = loginParam)
        return user?.mapToDomain()
    }

    override fun saveLoginUser(loginUserParams: LoginUserParams): Response {
        val userDTO = loginUserParams.mapToDTO()
        val result = localStorage.saveLoginUser(userDTO)
        return result
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

    override fun removeUserAllNotates(userLogName: String): Response {
        return localStorage.removeUserAllNotates(userLogin = userLogName)
    }

    override fun getNightMode(): Int {
        val resVal = preferencesStorage.getNightMode()
        return resVal
    }

    override fun saveNightMode(mode: Int) {
        preferencesStorage.saveNightMode(mode = mode)
    }
}