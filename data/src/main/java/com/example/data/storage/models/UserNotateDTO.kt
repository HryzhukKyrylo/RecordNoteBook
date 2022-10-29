package com.example.data.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.UserNotateModel

@Entity(tableName = "my_table")
data class UserNotateDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val userLogName: String,
    val title: String? = null,
    val log: String? = null,
    val pass: String? = null,
    val timeCreate: Long,
    val timeLastChange: Long,
)

fun UserNotateDTO.mapToDomain(): UserNotateModel {
    return UserNotateModel(
        userLogName = this.userLogName,
        title = this.title,
        log = this.log ?: "",
        pass = this.pass ?: "",
        timeLastChange = this.timeLastChange,
        timeCreate = this.timeCreate
    )
}