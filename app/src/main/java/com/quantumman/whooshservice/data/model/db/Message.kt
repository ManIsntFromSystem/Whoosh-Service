package com.quantumman.whooshservice.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.quantumman.whooshservice.data.local.converters.DateConverter
import com.quantumman.whooshservice.util.AppContract.TABLE_MESSAGE_NAME
import java.util.*

@Entity(tableName = TABLE_MESSAGE_NAME)
@TypeConverters(DateConverter::class)
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo (name = "date") val date: Calendar?,
    @ColumnInfo (name = "name") val name: String,
    @ColumnInfo (name = "status") val status: String,
    @ColumnInfo (name = "comments") val comments: String
)

//{			status: CHANGE_BATTERY,			comments: «Самокату требуется ремонт» 		}