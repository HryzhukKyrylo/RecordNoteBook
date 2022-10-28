package com.example.data.storage.localstorage

import androidx.room.*
import com.example.data.storage.models.UserNotateDTO

const val TABLE_NAME = "local_user_notates"

@Dao
interface UserNotateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserNotate(userNotate: UserNotateDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllUserNotate(userNotates: List<UserNotateDTO>)

    @Query("SELECT * FROM $TABLE_NAME WHERE userLogName IN (:userLogName)")
    fun getAllUserNotates(userLogName: String): List<UserNotateDTO>

    @Delete
    fun delete(user: UserNotateDTO)
}