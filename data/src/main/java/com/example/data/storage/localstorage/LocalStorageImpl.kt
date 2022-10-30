package com.example.data.storage.localstorage

import com.example.data.storage.models.UserNotateDTO

class LocalStorageImpl(
    private val database: UserDatabase,
) : LocalStorage {

    override fun getUserNotates(userLogNameParam: String): List<UserNotateDTO> {
        return database.userDao().getAllUserNotates(userLogName = userLogNameParam)
//        return testUserListNotates(name = userLogNameParam)
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

    override fun removeNotate(userDTO: UserNotateDTO): Boolean {
        val result = try {
            database.userDao().delete(userDTO)
            true
        } catch (ex: Exception) {
            false
        }
        return result
    }

    private fun testUserListNotates(name: String): List<UserNotateDTO> {
        val result = List<UserNotateDTO>(10) {
            UserNotateDTO(
                userLogName = "$name -$it",
                title = "title - $it",
                logData = "log - $it",
                privateInfo = "pass - $it",
                timeCreate = it.toLong(),
                timeLastChange = it.toLong()
            )
        }
        return result
    }
}