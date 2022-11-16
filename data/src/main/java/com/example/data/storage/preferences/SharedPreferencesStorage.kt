package com.example.data.storage.preferences

import com.example.data.storage.models.LoginUserDTO

interface SharedPreferencesStorage {
    fun saveLoginUser(loginUserParams: LoginUserDTO): Boolean
    fun getLoginUser(): LoginUserDTO
    fun getNightMode(): Int
    fun saveNightMode(mode: Int)

    companion object {
        const val NIGHT_MODE_ON = 1
        const val NIGHT_MODE_OFF = 2
        const val NIGHT_MODE_SYSTEM = 0
    }
}