package com.example.data.storage.localstorage

import androidx.room.*
import com.example.data.storage.models.UserNotateDTO

@Dao
interface UserNotateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserNotate(userNotate: UserNotateDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllUserNotate(userNotates: List<UserNotateDTO>)

    @Query("SELECT * FROM my_table WHERE userLogName IN (:userLogName)")
    fun getAllUserNotates(userLogName: String): List<UserNotateDTO>

    @Delete
    fun delete(user: UserNotateDTO)

    @Query("DELETE FROM my_table WHERE userLogName=:userLogName")
    fun deleteUserAllNotates(userLogName: String)

    @Query(
        "SELECT * FROM my_table WHERE userLogName LIKE :userLogNameParam AND " +
                "timeLastChange LIKE :timeLastChangeParam LIMIT 1"
    )
    fun getUserNotate(userLogNameParam: String, timeLastChangeParam: Long): UserNotateDTO
}