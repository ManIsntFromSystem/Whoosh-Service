package com.quantumman.whooshservice.ui.model

class StatusScooterDataItem(
    val id: Long,
    val date: String?,
    val name: String,
    val status: String,
    val comments: String
)

//    : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readLong(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeLong(id)
//        parcel.writeString(date)
//        parcel.writeString(name)
//        parcel.writeString(status)
//        parcel.writeString(comments)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<StatusScooterDataItem> {
//        override fun createFromParcel(parcel: Parcel): StatusScooterDataItem {
//            return StatusScooterDataItem(
//                parcel
//            )
//        }
//
//        override fun newArray(size: Int): Array<StatusScooterDataItem?> {
//            return arrayOfNulls(size)
//        }
//    }
//}