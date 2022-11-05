package com.example.data.storage.models

import androidx.room.Entity
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams

@Entity(tableName = "my_login_table", primaryKeys = ["login", "privateData"])
data class UserLoginDTO(
    val login: String,
    val privateData: String,
)

fun UserLoginDTO.mapToDomain(): LoginUserModel {
    return LoginUserModel(
        login = this.login,
        password = this.privateData,
    )
}

fun LoginUserParams.mapToDTO(): UserLoginDTO {
    return UserLoginDTO(
        login = this.loginParam,
        privateData = this.passwordParam,
    )
}