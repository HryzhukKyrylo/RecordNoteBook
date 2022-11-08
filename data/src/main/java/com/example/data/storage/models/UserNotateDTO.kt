package com.example.data.storage.models

import androidx.room.Entity
import com.example.data.utils.decodeData
import com.example.data.utils.encodeData
import com.example.domain.models.CreateUserParams
import com.example.domain.models.UserNotateModel

@Entity(tableName = "my_table", primaryKeys = ["userLogName", "timeCreate"])
data class UserNotateDTO(
    val userLogName: String,
    val title: String? = null,
    val logData: String? = null,
    val privateInfo: String? = null,
    val timeCreate: Long,
    val timeLastChange: Long,
)

fun UserNotateDTO.mapToDomain(): UserNotateModel {
    return UserNotateModel(
        userLogName = this.userLogName,
        title = this.title,
        logData = this.logData?.decodeData() ?: "",
        privateInfo = this.privateInfo?.decodeData() ?: "",
        timeLastChange = this.timeLastChange,
        timeCreate = this.timeCreate
    )
}

fun UserNotateModel.mapToDTO(): UserNotateDTO {
    return UserNotateDTO(
        userLogName = this.userLogName,
        title = this.title,
        logData = this.logData?.encodeData() ?: "",
        privateInfo = this.privateInfo?.encodeData() ?: "",
        timeLastChange = this.timeLastChange,
        timeCreate = this.timeCreate
    )
}

fun UserNotateDTO.copyDTOWithNewData(
    logData: String?,
    privateInfo: String?,
    timeLastChange: Long
): UserNotateDTO {
    return UserNotateDTO(
        userLogName = this.userLogName,
        title = this.title,
        logData = logData?.encodeData() ?: "",
        privateInfo = privateInfo?.encodeData() ?: "",
        timeLastChange = timeLastChange,
        timeCreate = this.timeCreate
    )
}

fun CreateUserParams.createDTO(): UserNotateDTO {
    return UserNotateDTO(
        userLogName = this.logName!!,
        title = this.title,
        logData = this.logData.encodeData(),
        privateInfo = this.privateInfo.encodeData(),
        timeCreate = this.createTimestamp,
        timeLastChange = this.lastTimestamp,
    )
}