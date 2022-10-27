package com.example.data.storage.preferences

import com.example.data.storage.models.LoginUserDTO

interface SharedPreferencesStorage {
    fun saveLoginUser(loginUserParams: LoginUserDTO): Boolean
    fun getLoginUser(): LoginUserDTO
}