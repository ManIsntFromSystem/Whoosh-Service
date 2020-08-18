package com.quantumman.whooshservice.util

import com.quantumman.whooshservice.data.model.db.Message
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem

fun List<Message>.toListStatusScooterDataItems(): List<StatusScooterDataItem> = this.map {
    StatusScooterDataItem(
        id = it.id!!,
        date = it.date?.fromCalendarToDisplay(),
        name = it.name,
        status = it.status,
        comments = it.comments
)}

fun List<StatusScooterDataItem>.toListMessages(): List<Message> = this.map {
    Message(
        id = it.id,
        date = it.date?.fromStringToCalendar(),
        name = it.name,
        status = it.status,
        comments = it.comments
    )}

fun Message.toStatusScooterDataItem() : StatusScooterDataItem = StatusScooterDataItem(
        id = this.id!!,
        date = this.date?.fromCalendarToDisplay(),
        name = this.name,
        status = this.status,
        comments = this.comments
    )

fun StatusScooterDataItem.toMessage(): Message = Message(
        id = this.id,
        date = this.date?.fromStringToCalendar(),
        name = this.name,
        status = this.status,
        comments = this.comments
    )