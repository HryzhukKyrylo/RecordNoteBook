package com.example.data.storage.preferences

import android.content.Context
import com.example.data.storage.models.LoginUserDTO

private const val PREFERENCES_NAME = "m_preferences"
private const val PREFERENCES_LOGIN_NAME = "m_login_preferences"
private const val PREFERENCES_PAS_NAME = "m_pas_preferences"

class SharedPreferencesStorageImpl(private val context: Context) : SharedPreferencesStorage {
    private val preferencesStorage =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun saveLoginUser(loginUserParams: LoginUserDTO): Boolean {
        preferencesStorage.edit()
            .putString(PREFERENCES_LOGIN_NAME, loginUserParams.login)
            .apply()
        preferencesStorage.edit()
            .putString(PREFERENCES_PAS_NAME, loginUserParams.password)
            .apply()
        return true
    }

    override fun getLoginUser(): LoginUserDTO {
        val login = preferencesStorage.getString(PREFERENCES_LOGIN_NAME, "") ?: ""
        val pas = preferencesStorage.getString(PREFERENCES_PAS_NAME, "") ?: ""
        return LoginUserDTO(login = login, password = pas)
    }
}