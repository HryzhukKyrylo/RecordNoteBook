package com.example.data.storage.localstorage

import com.example.data.storage.models.UserLoginDTO
import com.example.data.storage.models.UserNotateDTO

class LocalStorageImpl(
    private val database: UserDatabase,
) : LocalStorage {

    override fun getUserNotates(userLogNameParam: String): List<UserNotateDTO> {
        return database.userDao().getAllUserNotates(userLogName = userLogNameParam)
    }

    override fun getNotate(userLogNameParam: String, timeLastChangeParam: Long): UserNotateDTO {
        return database.userDao().getUserNotate(userLogNameParam, timeLastChangeParam)
    }

    override fun saveUserNotate(userDTO: UserNotateDTO): Boolean {
        val result = try {
            database.userDao().saveUserNotate(userDTO)
            true
        } catch (ex: Exception) {
            false
        }
        return result
    }

    override fun saveAllUserNotates(userNotates: List<UserNotateDTO>) {
        database.userDao().saveAllUserNotate(userNotates)
    }

    override fun removeNotate(userDTO: UserNotateDTO): Boolean {
        val result = try {
            database.userDao().delete(userDTO)
            true
        } catch (ex: Exception) {
            false
        }
        return result
    }

    override fun getUserLogin(userLogName: String): UserLoginDTO? {
        return database.userLoginDao()
            .getUserLogin(userLogNameParam = userLogName)
    }

    override fun saveLoginUser(userLogin: UserLoginDTO) {
        database.userLoginDao().saveUserLogin(userNotate = userLogin)
    }

    override fun removeUserAllNotates(userLoginName: String) {
        //todo refactored
        database.userDao().deleteUserAllNotates(userLoginName)
    }

    override fun removeUserLogin(userLoginName: String): Boolean {
        val oldUser = database.userLoginDao().getUserLogin(userLoginName)
        val res = oldUser?.let {
            database.userLoginDao().delete(oldUser)
            true
        } ?: false
        return res
    }

}