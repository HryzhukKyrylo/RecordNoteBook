package com.example.data.storage.localstorage

import com.example.data.storage.models.UserNotateDTO

class LocalStorageImpl(private val userDAO: UserNotateDao) : LocalStorage {

    override fun getUserNotates(userLogNameParam: String): List<UserNotateDTO> {
        return userDAO.getAllUserNotates(userLogName = userLogNameParam)
    }
}