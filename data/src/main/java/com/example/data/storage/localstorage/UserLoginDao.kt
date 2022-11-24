package com.example.data.storage.localstorage

import androidx.room.*
import com.example.data.storage.models.UserLoginDTO

@Dao
interface UserLoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserLogin(userNotate: UserLoginDTO)

    @Query("SELECT * FROM my_login_table WHERE login IN (:userLogName)")
    fun getAllUserLogins(userLogName: String): List<UserLoginDTO>

    @Delete
    fun delete(user: UserLoginDTO)

    @Query(
        "SELECT * FROM my_login_table WHERE login LIKE :userLogNameParam "
    )
    fun getUserLogin(userLogNameParam: String): UserLoginDTO?
}