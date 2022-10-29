package com.example.data.storage.localstorage

import com.example.data.storage.models.UserNotateDTO

class LocalStorageImpl(
    private val database: UserDatabase,
) : LocalStorage {

    override fun getUserNotates(userLogNameParam: String): List<UserNotateDTO> {
//        return database.userDao().getAllUserNotates(userLogName = userLogNameParam)
        return testUserListNotates(name = userLogNameParam)
    }

    private fun testUserListNotates(name: String): List<UserNotateDTO> {
        val result = List<UserNotateDTO>(10) {
            UserNotateDTO(
                id = it.toLong(),
                userLogName = "$name -$it",
                title = "title - $it",
                log = "log - $it",
                pass = "pass - $it",
                timeCreate = it.toLong(),
                timeLastChange = it.toLong()
            )
        }
        return result
    }
}