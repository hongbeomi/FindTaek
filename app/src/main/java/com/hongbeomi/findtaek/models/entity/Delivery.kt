package com.hongbeomi.findtaek.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "delivery")
data class Delivery (
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var fromName: String,
    var fromTime: String,
    var toName: String,
    var toTime: String,
    var carrierId: String,
    var carrierName: String,
    var productName: String,
    var trackId: String,
    var status: String
)