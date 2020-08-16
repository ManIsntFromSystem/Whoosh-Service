package com.quantumman.whooshservice.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.quantumman.whooshservice.util.AppContract.TABLE_MESSAGE_NAME

@Entity(tableName = TABLE_MESSAGE_NAME)
data class Message(
    @field:PrimaryKey(autoGenerate = true) val id: Int,
    val date: Long,
    val name: String,
    val status: String,
    val comments: String
)

//{			status: CHANGE_BATTERY,			comments: «Самокату требуется ремонт» 		}