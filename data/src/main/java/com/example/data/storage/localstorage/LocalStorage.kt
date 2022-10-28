package com.example.data.storage.localstorage

import com.example.data.storage.models.UserNotateDTO

interface LocalStorage {
    fun getUserNotates(userLogNameParam: String):List<UserNotateDTO>
}